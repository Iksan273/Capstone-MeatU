from flask import Flask, jsonify, request, json
from flask_sqlalchemy import SQLAlchemy
from flask_mysqldb import MySQL
import bcrypt
from flask_marshmallow import Marshmallow
app = Flask(__name__)
app.config['JSON_SORT_KEYS'] = False

#configure connection Mysql
app.config['MYSQL_HOST'] = '34.101.128.44'
app.config['MYSQL_USER'] = 'myuser'
app.config['MYSQL_PASSWORD'] = 'mypass'
app.config['MYSQL_DB'] = 'meatu'
app.config['MYSQL_CURSORCLASS'] = 'DictCursor'
mysql = MySQL(app)

@app.route('/register', methods=['POST'])
def register():
    # Get registration data from the request
    data = request.get_json()
    nama = data['nama']
    alamat = data['alamat']
    email = data['email']
    password = data['password']

    # Encrypt the password
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())

    # Store the registration data in the database
    cur = mysql.connection.cursor()
    cur.execute("INSERT INTO users (nama, alamat, email, password) VALUES (%s, %s, %s, %s)",
    (nama, alamat, email, hashed_password))
    mysql.connection.commit()

    # Build the response
    response = {
        'message': 'Registration successful',
        'nama': nama,
        'alamat':alamat,
        'email': email
    }
    return jsonify(response), 201


@app.route('/login', methods=['POST'])
def login():
    # Mendapatkan data login dari request
    data = request.get_json()
    email = data['email']
    password = data['password']

    # Mengecek kecocokan email dan password di database
    cur = mysql.connection.cursor()
    query = "SELECT * FROM users WHERE email=%s"
    cur.execute(query, (email,))
    user = cur.fetchone()
    cur.close()

    # Menangani hasil autentikasi
    if user is None:
        return jsonify({'message': 'Email atau password salah'}), 401
    if bcrypt.checkpw(password.encode('utf-8'), user['password'].encode('utf-8')):
            # Data pengguna yang berhasil login
            user_data = {
                'id': user['id'],
                'nama': user['nama'],
                'alamat':user['alamat'],
                'email': user['email']
                }
            return jsonify({'message':'Login berhasil'}, user_data), 200
    else:
        return jsonify({'message': 'Email atau password salah'}), 401

@app.route('/users/<id>', methods=['GET'])
def get_user(id):
    cur = mysql.connection.cursor()
    cur.execute("SELECT * FROM users WHERE id = %s", (id,))
    user = cur.fetchone()
    cur.close()

    if user:
        user_dict = {
            'id': user['id'],
            'nama': user['nama'],
            'alamat': user['alamat'],
            'email': user['email']
        }
        return jsonify(user_dict)
    else:
        return jsonify({'message': 'User not found'})

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
if __name__ == '__main__':
    app.run(debug=True)
