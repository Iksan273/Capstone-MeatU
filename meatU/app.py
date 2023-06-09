from flask import Flask, jsonify, request, json
from flask_sqlalchemy import SQLAlchemy
import pymysql
import bcrypt
from flask_marshmallow import Marshmallow
from config import MYSQL_HOST, MYSQL_USER, MYSQL_PASSWORD, MYSQL_DB
app = Flask(__name__)
app.config['JSON_SORT_KEYS'] = False

#configure connection Mysql
db = pymysql.connect(
    host = MYSQL_HOST,
    user =  MYSQL_USER,
    password = MYSQL_PASSWORD,
    database =MYSQL_DB
)
@app.route('/login', methods=['POST'])
def login():
    # Mendapatkan data login dari request
    data = request.get_json()
    email = data['email']
    password = data['password']

    # Mengecek kecocokan email dan password di database
    cur = db.cursor()
    query = "SELECT * FROM users WHERE email=%s AND password=%s"
    cur.execute(query, (email, password))
    user = cur.fetchone()

    # Menangani hasil autentikasi
    if user is None:
        return jsonify({'message': 'Email atau password salah'}), 401
    else:
        # Mengambil data pengguna yang berhasil login
        user_data = {
        'id': user[0],
        'nama': user[1],
        'alamat': user[2],
        'email': user[3]
    }
        return jsonify(user_data), 200
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
if __name__ == '__main__':
    app.run(debug=True)
