from flask import Flask, jsonify, request, json
from flask_sqlalchemy import SQLAlchemy
import pymysql
import bcrypt
from flask_marshmallow import Marshmallow
app = Flask(__name__)
app.config['JSON_SORT_KEYS'] = False

#configure connection Mysql
db = pymysql.connect(
    host = 'localhost',
    user =  'root',
    password = '',
    database = 'meatu'
)
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
    cur = db.cursor()
    cur.execute("INSERT INTO users (nama, alamat, email, password) VALUES (%s, %s, %s, %s)",
    (nama, alamat, email, hashed_password))
    db.commit()

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
    cur = db.cursor()
    query = "SELECT * FROM users WHERE email=%s"
    cur.execute(query, (email,))
    user = cur.fetchone()


    # Menangani hasil autentikasi
    if user is None:
        return jsonify({'message': 'Email atau password salah'}), 401
    hashed_password = user[4]
    if bcrypt.checkpw(password.encode('utf-8'), hashed_password.encode('utf-8')):
        # Mengambil data pengguna yang berhasil login
        user_data = {
        'id': user[0],
        'nama': user[1],
        'alamat': user[2],
        'email': user[3]
    }
        return jsonify({'message':'Login berhasil'}, user_data), 200
    else:
        return jsonify({'message': 'Email atau password salah'}), 401

@app.route('/users/<id>', methods=['GET'])
def get_user(id):
    cur = db.cursor()
    cur.execute("SELECT * FROM users WHERE id = %s", (id,))
    user = cur.fetchone()
    cur.close()

    if user:
        user_dict = {
            'id': user[0],
            'nama': user[1],
            'alamat': user[2],
            'email': user[3]
        }
        return jsonify(user_dict)
    else:
        return jsonify({'message': 'User not found'})

@app.route('/users/<id>/history', methods=['GET'])
def get_user_history(id):
    cur = db.cursor()
    cur.execute("SELECT * FROM history_user WHERE user_id = %s", (id,))
    history = cur.fetchall()
    cur.close()

    if history:
        history_list = []
        for entry in history:
            history_dict = {
                'id': entry[0],
                'user_id': entry[1],
                'link_gambar': entry[2],
                'tanggal': entry[3].strftime('%Y-%m-%d'),
                'hasil_prediksi': entry[4]
            }
            history_list.append(history_dict)

        return jsonify(history_list)
    else:
        return jsonify({'message': 'User history not found'})
if __name__ == '__main__':
    app.run(debug=True)
