from flask import Flask, jsonify, request
from flask_mysqldb import MySQL
from flask_marshmallow import Marshmallow
import bcrypt

app = Flask(__name__)
app.config['JSON_SORT_KEYS'] = False
app.config['MYSQL_HOST'] = '34.101.128.44'
app.config['MYSQL_USER'] = 'myuser'
app.config['MYSQL_PASSWORD'] = 'mypass'
app.config['MYSQL_DB'] = 'meatu'
app.config['MYSQL_CURSORCLASS'] = 'DictCursor'
mysql = MySQL(app)
ma = Marshmallow(app)

class UserSchema(ma.Schema):
    class Meta:
        fields = ('id', 'nama', 'alamat', 'email')

user_schema = UserSchema()
users_schema = UserSchema(many=True)

class User:
    def __init__(self, nama, alamat, email, password):
        self.nama = nama
        self.alamat = alamat
        self.email = email
        self.password = password

@app.route('/register', methods=['POST'])
def register():
    try:
        data = request.get_json()
        errors = validate_registration_data(data)
        if errors:
            return jsonify({'message': 'Validation Error', 'errors': errors}), 400

        nama = data['nama']
        alamat = data['alamat']
        email = data['email']
        password = data['password']
        hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())

        cur = mysql.connection.cursor()
        cur.execute("INSERT INTO users(nama, alamat, email, password) VALUES(%s, %s, %s, %s)",
                    (nama, alamat, email, hashed_password))
        mysql.connection.commit()
        cur.close()

        response = {
            'message': 'Registration successful',
            'nama': nama, 'alamat': alamat, 'email': email
        }
        return jsonify(response), 201
    except Exception as e:
        return jsonify({'message': 'Internal Server Error'}), 500

@app.route('/login', methods=['POST'])
def login():
    try:
        data = request.get_json()
        errors = validate_login_data(data)
        if errors:
            return jsonify({'message': 'Validation Error', 'errors': errors}), 400

        email = data['email']
        password = data['password']

        cur = mysql.connection.cursor()
        cur.execute("SELECT * FROM users WHERE email = %s", (email,))
        user = cur.fetchone()
        cur.close()

        if user is None:
            return jsonify({'message': 'Email atau password salah'}), 401
        if bcrypt.checkpw(password.encode('utf-8'), user['password'].encode('utf-8')):
            # Data pengguna yang berhasil login
            user_data = {
                'message': 'Login successful',
                'id': user['id'],
                'nama': user['nama'],
                'alamat':user['alamat'],
                'email': user['email']
                }
            return jsonify(user_data), 200
    except Exception as e:
        return jsonify({'message': 'Internal Server Error'}), 500

@app.route('/users/<id>', methods=['GET'])
def get_user(id):
    try:
        cur = mysql.connection.cursor()
        cur.execute("SELECT * FROM users WHERE id = %s", (id,))
        user = cur.fetchone()
        cur.close()
        if user is None:
            return jsonify({'message': 'user not found'}), 401
        if user:
            user_dict = {
            'id': user['id'],
            'nama': user['nama'],
            'alamat': user['alamat'],
            'email': user['email']
        }
        return jsonify(user_dict)
    except Exception as e:
        return jsonify({'message': 'Internal Server Error'}), 500

@app.route('/users/<id>/history', methods=['GET'])
def get_user_history(id):
    cur = mysql.connection.cursor()
    cur.execute("SELECT * FROM history_user WHERE user_id = %s", (id,))
    history = cur.fetchall()
    cur.close()

    if history:
        history_list = []
        for entry in history:
            history_dict = {
                'id': entry['id'],
                'user_id': entry['user_id'],
                'link_gambar': entry['link_gambar'],
                'tanggal': entry['tanggal'].strftime('%Y-%m-%d'),
                'hasil_prediksi': entry['hasil_prediksi']
            }
            history_list.append(history_dict)

        return jsonify(history_list)
    else:
        return jsonify({'message': 'User history not found'})
        
# Validation functions
def validate_registration_data(data):
    errors = []
    if 'nama' not in data:
        errors.append({'field': 'nama', 'message': 'Nama is required'})
    if 'alamat' not in data:
        errors.append({'field': 'alamat', 'message': 'Alamat is required'})
    if 'email' not in data:
        errors.append({'field': 'email', 'message': 'Email is required'})
    elif check_email_exists(data['email']):
        errors.append({'field': 'email', 'message': 'Email is already taken'})
    if 'password' not in data:
        errors.append({'field': 'password', 'message': 'Password is required'})
    return errors

def validate_login_data(data):
    errors = []
    if 'email' not in data:
        errors.append({'field': 'email', 'message': 'Email is required'})
    if 'password' not in data:
        errors.append({'field': 'password', 'message': 'Password is required'})
    return errors

def check_email_exists(email):
    cur = mysql.connection.cursor()
    cur.execute("SELECT * FROM users WHERE email = %s", (email,))
    user = cur.fetchone()
    cur.close()
    return user is not None

if __name__ == '__main__':
    app.run(debug=True)
