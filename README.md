# TUGAS BESAR 1 STRATEGI ALGORITMA - IF2211
> Tugas Besar 1: Pemanfaatan Algoritma Greedy dalam Aplikasi Permainan “Galaxio”

## Anggota Kelompok
<table>
    <tr>
        <td colspan="3", align = "center"><center>Nama Kelompok: JAS-029</center></td>
    </tr>
    <tr>
        <td>No.</td>
        <td>Nama</td>
        <td>NIM</td>
    </tr>
    <tr>
        <td>1.</td>
        <td>Jason Rivalino</td>
        <td>13521008</td>
    </tr>
    <tr>
        <td>2.</td>
        <td>Muhamad Salman Hakim Alfarisi</td>
        <td>13521010</td>
    </tr>
    <tr>
        <td>3.</td>
        <td>Afnan Edsa Ramadhan</td>
        <td>13521011</td>
    </tr>
</table>

## Table of Contents
* [Deskripsi Singkat](#deskripsi-singkat)
* [Struktur File](#struktur-file)
* [Requirements](#requirements)
* [Cara Menjalankan Program](#cara-menjalankan-program)
* [Video Demo Penjelasan](#video-demo-penjelasan)
* [Acknowledgements](#acknowledgements)
* [Foto Bersama](#foto-bersama)

## Deskripsi Singkat 
Galaxio adalah sebuah game battle royale yang mempertandingkan bot kapal anda dengan beberapa bot kapal yang lain. Setiap pemain akan memiliki sebuah bot kapal dan tujuan dari permainan adalah agar bot kapal anda yang tetap hidup hingga akhir permainan. Dalam tugas besar ini, akan diimplementasikan Strategi Algoritma pada game engine Galaxio dengan menggunakan bahasa pemrograman Java. Impementasi dilakukan pada bot kapal dengan menggunakan Algoritma Greedy agar bot kapal dapat memenangkan permainan.


## Struktur File
```bash
📦Tubes1_JAS-029
 ┣ 📂doc
 ┃ ┗ 📜JAS-029_029.pdf
 ┣ 📂src/main/java
 ┃ ┣ 📂Enums
 ┃ ┣ ┣ 📜ObjectTypes.java
 ┃ ┣ ┗ 📜PlayerActions.java
 ┃ ┣ 📂Models
 ┃ ┣ ┣ 📜GameObject.java
 ┃ ┣ ┣ 📜GameState.java
 ┃ ┣ ┣ 📜GameStateDto.java
 ┃ ┣ ┣ 📜PlayerAction.java
 ┃ ┣ ┣ 📜Position.java
 ┃ ┣ ┗ 📜World.java
 ┃ ┣ 📂Services
 ┃ ┣ ┗ 📜BotService.java
 ┃ ┣ 📜Main.java 
 ┣ 📂target
 ┃ ┗ 📜JAS-029_029.jar
 ┣ 📜Dockerfile
 ┣ 📜pom.xml
 ┗ 📜README.md
 ```
## Requirements
1. Java ( minimal Java 11 )
2. Maven ( jika ingin mengubah file `.jar` )
3. .NET Core 3.1 dan .NET Core 5 ( jika membutuhkan reference bot )

## Cara Menjalankan Program
Langkah-langkah proses setup program adalah sebagai berikut:
1. Download starter-pack.zip yang terdapat pada link berikut -> [(Link Starter-Pack)](https://github.com/EntelectChallenge/2021-Galaxio/releases/tag/2021.3.2)
2. Extract starter-pack.zip yang sudah didownload
3. Clone Repository Github ini pada `..\starter-pack\starter-bots`
4. Download file run.bat yang ada pada link berikut -> [(Link Runner)](https://drive.google.com/file/d/1MDKsT4PWi_Ag_GkloMwkZPAvl_bnMK-4/view?usp=share_link)
5. Pindahkan file run.bat yang sudah didownload pada `..\starter-pack`
6. Jalankan file run.bat hingga program selesai, jika sudah selesai bisa dicek pada `..\starter-pack\logger-publish`, akan terbentuk dua file GameStateLog.json baru
7. Copy address dari lokasi file ini `..\starter-pack\logger-publish`
8. Extract program Galaxio yang terdapat pada `..\starter-pack\visualiser`
9. Jalankan program Galaxio yang sudah diekstrak sebelumnya
10. Pada menu Game Galaxio, pilih menu options dan paste copy address lokasi file pada step nomor 7 di log files location

![log_file_location](https://user-images.githubusercontent.com/91790457/218540322-fae3cf2e-c55f-47fb-b40f-d3292b64a0f2.png)

11. Untuk melihat permainan, pilih menu load dan pilih data GameStateLog.json yang telah terbentuk sebelumnya

![load_log](https://user-images.githubusercontent.com/91790457/218541925-338ca780-1a1e-4066-a105-9f349418cadb.png)



12. Klik start untuk melihat hasil permainan
13. Jika ingin bermain lagi, bisa menjalankan file run.bat kembali dan membuka program Galaxio serta melakukan load file seperti step sebelumnya


<p align="center"><b>SELAMAT BERMAIN DAN BERSENANG-SENANG :)</b></p>

## Video Demo Penjelasan
Link: https://youtu.be/6f4_4flntqM

## Acknowledgements
- Tuhan Yang Maha Esa
- Dosen Mata Kuliah yaitu Pak Rinaldi (K1), Bu Ulfa (K2), dan Pak Rila (K3)
- Kakak-Kakak Asisten Mata Kuliah Strategi Algoritma IF2211

## Foto Bersama
![S__8781826](https://user-images.githubusercontent.com/91790457/219616892-8d468020-6479-4143-bc2c-6fa1e2303d2b.jpg)

