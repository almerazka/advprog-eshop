**Tutorial Pemrograman Lanjut (Advanced Programming) 2024/2025 Genap**
* Nama    : Muhammad Almerazka Yocendra
* NPM     : 2306241745
* Kelas   : Pemrograman Lanjut - A

<details>
<summary>Module 1: Coding Standard</summary>

## Reflection 1

### Penerapan Prinsip _Clean Code_
**1. _Clear and Meaningful Names_**

Dalam tutorial ini, saya menggunakan nama yang deskriptif untuk variabel, kelas, method, dan parameter. Dengan penamaan yang jelas, saya tidak perlu lagi menambahkan komentar untuk menjelaskan tujuan dari keempat elemen tersebut. Contoh :
- method names: `create`, `edit`, `delete`, `findAll`, `findProductById`
- parameter names: `productId`, `newProduct`
- class names: `ProductController`, `ProductService`, `ProductRepository`
- Variabel juga sudah mengikuti konvensi penamaan Java
  
**2. _DRY (Don't Repeat Yourself)_**

Di `ProductRepository`, terdapat metode validasi yang digunakan ulang
```java
private String validateAndSanitizeName(String name)
private double validateQuantity(double quantity)
```
Metode ini digunakan di dua tempat yaitu saat membuat produk baru (_create_) dan saat mengedit produk (_edit_). Method lain yang dipaki berulang kali adalah method `findProductById` yang berfungsi untuk mencari produk dengan id yang ada.

**3. _Object dan Data Structure_**

UUID sebaiknya dibuat di _constructor_ class Product karena setiap Product harus memiliki ID saat dibuat, tidak perlu menunggu sampai di Repository untuk memiliki identitas. Hal ini juga lebih sesuai dengan prinsip encapsulation
```java
public Product() {
        this.productId = UUID.randomUUID().toString();
    }
```

**4. _One Function One Task Principle_**

Setiap method dirancang untuk melakukan satu tugas spesifik, misalnya `createProductPage` hanya bertanggung jawab menampilkan halaman _create product_, `productListPage` hanya bertanggung jawab menampilkan _list product_, dan lain sebagainya.

### Secure Coding Practices
**1. Input Validation**
- Validasi dan sanitasi nama produk di `ProductRepository`
- Validasi kuantitas untuk mencegah nilai numerik yang tidak valid (negatif/angka)
- Perlindungan terhadap nilai kosong atau null
```java
// Metode untuk validasi kuantitas produk (tidak boleh huruf)
    private double validateQuantity(double quantity) {
        if (Double.isNaN(quantity) || Double.isInfinite(quantity)) {
            return 0; // Jika bukan angka, ubah menjadi 0
        }
        return Math.max(quantity, 0); // Jika negatif, ubah ke 0
    }
```

**2. Preventing Injection**
- Sanitasi karakter khusus HTML dalam nama produk
- Penghapusan karakter yang berpotensi berbahaya menggunakan regex: [<>%$]
```java
// Metode untuk validasi dan sanitasi nama produk
    private String validateAndSanitizeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Product not found";
        }
        return name.replaceAll("[<>%$]", "");
    }
```

**3. Safe Data Handling**
- Pembuatan UUID untuk ID produk
- Pemeriksaan null yang tepat di seluruh basis kode
```java
public Product() {
        this.productId = UUID.randomUUID().toString();
    }
```

### Mistakes Found and Advice
1. Implementasi di awal mengizinkan semua masukan/input, termasuk masukan yang berpotensi berbahaya. Solusinya dengan menerapkan validasi untuk nama dan kuantitas.
2. Form input di _CreateProduct.html_ dan _EditProduct.html_ masih memiliki duplikasi sehingga masih bisa di `refactor` supaya lebih efisien.
3. Jika menggunakan ID yang mudah ditebak, sistem dapat lebih rentan terhadap penyalahgunaan. Solusinya, memakai UUID karena unik dan sulit diprediksi, jadi lebih aman.
4. Jika produk yang diminta tidak valid, method mengembalikan nilai nol tanpa penjelasan. Solusinya lemparkan penjelasan atau peringatan yang berarti sebagai gantinya.
5. Method _delete_ mengizinkan menghapus item tanpa memeriksa apakah item tersebut ada atau tidak. Solusinya verifikasi produk sebelum mencoba menghapus.
6. Beberapa logika validasi diulang di dalam metode yang berbeda. Solusinya ekstrak logika validasi ke dalam metode terpisah yang dapat digunakan kembali.
7. Penggunaan `ArrayList` untuk menyimpan data product yang kurang efisien dibandingkan menggunakan struktur data yang lebih optimal seperti `HashMap` atau `TreeMap` untuk pencarian yang lebih cepat.


### How Improve Code?
Kalau mau meningkatkan kode, pertama saya pahami dulu alur dan fungsinya, lalu cek apakah ada bug, duplikasi, atau bagian yang kurang efisien. Saya juga berusaha membuat kode lebih rapi dengan memberi nama variabel yang jelas, memecah logika kompleks ke dalam metode atau kelas yang lebih spesifik, dan menghindari duplikasi. 
Selain itu, saya juga memastikan keamanan kode dengan validasi input, memakai UUID untuk ID unik, dan mencegah celah keamanan seperti SQL Injection atau XSS. ika masih ada kendala, saya cari solusi di forum seperti Discord Advanced Programming, Stack Overflow, atau Google. Kalau belum terpecahkan, saya tanya asisten dosen atau teman, sambil mencoba alternatif lain seperti bantuan AI seperti ChatGPT.
