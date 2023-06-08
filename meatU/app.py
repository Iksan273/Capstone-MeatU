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

#REGISTTTT
@app.route('/register', methods=['POST'])
def register():
    data = request.get_json()
    nama = data['nama']
    email = data['email']
    password = data['password']

    # Mengecek apakah email sudah terdaftar
    cursor = db.cursor()
    query = "SELECT * FROM users WHERE email=%s"
    cursor.execute(query, (email,))
    existing_user = cursor.fetchone()

    if existing_user:
        return jsonify({'message': 'Email sudah terdaftar'}), 409
    # enkripsi password sebelum disimpan
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())

    # Menyimpan pengguna baru ke dalam database
    insert_query = "INSERT INTO users (nama, email, password) VALUES (%s, %s, %s)"
    cursor.execute(insert_query, (nama, email, hashed_password))
    db.commit()

    return jsonify({'message': 'Registrasi berhasil'}), 201

#LOGINNNNN
@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    email = data['email']
    password = data['password']

    cursor = db.cursor()    
    query = "SELECT * FROM users WHERE email=%s AND password=%s"
    cursor.execute(query, (email,))
    user = cursor.fetchone()

    if user is None or not bcrypt.checkpw(password.encode('utf-8'), user[5].encode('utf-8')):
        return jsonify({'message': 'Email atau password salah'}), 401

    user_data = {
        'id': user[0],
        'nama': user[1],
        'no-hp': user[2],
        'alamat': user[3],
        'email': user[4]
    }
    return jsonify(user_data), 200

if __name__ == '__main__':
    app.run(debug=True)
