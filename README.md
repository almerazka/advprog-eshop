**Tutorial Pemrograman Lanjut (Advanced Programming) 2024/2025 Genap**
* Nama    : Muhammad Almerazka Yocendra
* NPM     : 2306241745
* Kelas   : Pemrograman Lanjut - A

Klik link di bawah untuk melihat aplikasi!
### [E-Shop](https://eshop-almerazka.koyeb.app/)

### SonarCloud Report
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=almerazka_advprog-eshop&metric=coverage)](https://sonarcloud.io/summary/new_code?id=almerazka_advprog-eshop)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=almerazka_advprog-eshop&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=almerazka_advprog-eshop)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=almerazka_advprog-eshop&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=almerazka_advprog-eshop)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=almerazka_advprog-eshop&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=almerazka_advprog-eshop)

### Coverage Report
![image](https://github.com/user-attachments/assets/21536243-9691-436c-b517-34de5bcb0312)

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
- Penghapusan karakter yang berpotensi berbahaya menggunakan **regex : [<>%$]**
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
...
 if (productToEdit == null) {
            return null;
        }

        // Validasi dan update data
        productToEdit.setProductName(validateAndSanitizeName(newProduct.getProductName()));
        productToEdit.setProductQuantity(validateQuantity(newProduct.getProductQuantity()));
        return productToEdit;
```

### Mistakes Found and Advice
1. Implementasi di awal mengizinkan semua masukan/input, termasuk masukan yang berpotensi berbahaya. Solusinya dengan menerapkan validasi untuk nama dan kuantitas.
2. Form input di _CreateProduct.html_ dan _EditProduct.html_ masih memiliki duplikasi sehingga masih bisa di `refactor` supaya lebih efisien.
3. Jika menggunakan ID yang mudah ditebak, sistem dapat lebih rentan terhadap penyalahgunaan. Solusinya, memakai UUID karena unik dan sulit diprediksi, jadi lebih aman.
4. Jika produk yang diminta tidak valid, method mengembalikan nilai nol tanpa penjelasan. Solusinya lemparkan penjelasan atau peringatan yang berarti sebagai gantinya.
5. Method _delete_ mengizinkan menghapus item tanpa memeriksa apakah item tersebut ada atau tidak. Solusinya verifikasi produk sebelum mencoba menghapus.
6. Beberapa logika validasi diulang di dalam metode yang berbeda. Solusinya ekstrak logika validasi ke dalam metode terpisah yang dapat digunakan kembali.
7. Penggunaan `ArrayList` untuk menyimpan data product yang kurang efisien dibandingkan menggunakan struktur data yang lebih optimal seperti `HashMap` atau `TreeMap` untuk pencarian yang lebih cepat.
8. Untuk mengikuti best practice dalam `RESTful API`, kita bisa mengganti metode `GET` dan `POST` dengan `PUT` untuk edit dan `DELETE` untuk hapus. Ini akan lebih sesuai dengan standar HTTP.

### How To Improve Code?
Kalau mau meningkatkan kode, pertama saya pahami dulu alur dan fungsinya, lalu cek apakah ada bug, duplikasi, atau bagian yang kurang efisien. Saya juga berusaha membuat kode lebih rapi dengan memberi nama variabel yang jelas, memecah logika kompleks ke dalam metode atau kelas yang lebih spesifik, dan menghindari duplikasi. 
Selain itu, saya juga memastikan keamanan kode dengan validasi input, memakai UUID untuk ID unik, dan mencegah celah keamanan seperti SQL Injection atau XSS. Jika masih ada kendala, saya cari solusi di forum seperti Discord Advanced Programming, Stack Overflow, atau Google. Kalau belum terpecahkan, saya akan mencoba bertanya kepada asisten dosen atau teman, sambil mencoba alternatif lain seperti bantuan AI seperti ChatGPT.

## Reflection 2
### _Unit & Functional Testing_
1. **_How Do I Feel After Writing Unit Tests?_**

Setelah menulis unit test, saya merasa lebih yakin terhadap kualitas kode yang saya buat. Unit test memungkinkan saya untuk menguji fitur tanpa harus membuka dan mengeceknya secara manual, cukup dengan menjalankan test yang sudah disiapkan oleh fitur tersebut. Selain itu, dengan adanya unit test, proses _debugging_ menjadi lebih mudah, karena jika tiba-tiba terdapat kesalahan, kita bisa langsung mengetahui bagian mana yang bermasalah. Hal ini tidak hanya menghemat waktu, tetapi juga memastikan bahwa setiap perubahan dalam kode tetap berjalan dengan benar.

2. **_How Many Unit Tests Should Be in a Class?_**

Menurut saya, tidak ada batasan pasti mengenai jumlah unit test dalam sebuah class. Semakin banyak test yang dibuat, semakin baik, asalkan tetap relevan dan tidak berlebihan. Idealnya, setiap metode atau fitur utama dalam kelas harus memiliki setidaknya satu atau lebih pengujian unit, entah itu kita mau nguji skenario positif, negatif, ataupun batasnya. 

3. **How to Make Sure Our Unit Tests Are Enough?**
   
Salah satu cara memastikan unit test mencakup seluruh bagian kode adalah dengan menggunakan _code coverage_, yang mengukur sejauh mana kode telah diuji dalam bentuk persentase. Berdasarkan referensi yang saya baca, _code coverage_ sekitar 80% sudah cukup baik, seperti yang diterapkan dalam mata kuliah PBP kemarin. Namun, perlu diingat bahwa code coverage 100% tidak selalu menjamin kode bebas dari bug, karena beberapa skenario edge case mungkin masih terlewat.

4. **Refleksi Tentang Clean Code dalam Functional Test**
   
Setelah menulis _CreateProductFunctionalTest.java_, muncul kebutuhan untuk menambahkan functional test lain, seperti pengujian fitur edit produk. Jika kita membuat kelas baru dengan setup dan variabel instance yang sama seperti sebelumnya, hal ini dapat mengurangi kualitas kode karena terjadi duplikasi. Akibatnya, prinsip DRY (Don't Repeat Yourself) tidak diterapkan dengan baik. Untuk mengatasi ini, sebaiknya gunakan _base test class_ agar setup dapat digunakan ulang tanpa harus menyalin kode di setiap test suite. Selain itu, _parameterized tests_ dapat digunakan untuk menghindari pengulangan test case yang memiliki pola serupa.

</details>
<details>
<summary>Module 2: CI/CD & DevOps</summary>

## Reflection
### Daftar _Code Quality Issue_ yang telah diperbaiki
#### 1. **Duplicate String Literal**
📌 **Permasalahan** :

Dalam kode _ProductController.java_, string `"redirect:/product/list"` digunakan berulang kali di berbagai metode. 

🔍 **Mengapa ini menjadi masalah?**

Menggunakan string literal berulang kali melanggar prinsip **DRY (Don't Repeat Yourself)**, meningkatkan risiko bug, dan menyulitkan pengelolaan kode. Jika URL redirect perlu diubah, setiap instance harus diedit secara manual, berpotensi menimbulkan inkonsistensi yang menyebabkan error. Kesalahan kecil seperti tambahan / atau perbedaan kapitalisasi dapat membuat aplikasi tidak berfungsi dengan benar. Hardcoding juga membuat perubahan lebih sulit, karena harus mencari dan mengganti semua instance string di berbagai lokasi, yang rentan terhadap kesalahan dan inkonsistensi.

❌ **Sebelum Perbaikan**
```java
@GetMapping("/edit/{productId}")
public String editProductPage(@PathVariable String productId, Model model) {
    Product product = service.findProductById(productId);
    if (product == null) {
        return "redirect:/product/list"; // DUPLIKASI #1
    }
    model.addAttribute("product", product);
    return "EditProduct";
}
```
✅ **Solusi** : Menggunakan Constant
```java
private static final String REDIRECT_PRODUCT_LIST = "redirect:/product/list";

@GetMapping("/edit/{productId}")
public String editProductPage(@PathVariable String productId, Model model) {
    Product product = service.findProductById(productId);
    if (product == null) {
        return REDIRECT_PRODUCT_LIST;
    }
    model.addAttribute("product", product);
    return "EditProduct";
}
```

#### 2. **Manual Exception Handling**
📌 **Permasalahan** :

Metode `contextLoads()` kosong tanpa penjelasan atau fungsionalitas yang jelas, yang dapat membingungkan pengembang lain. SonarCloud atau alat analisis kode lainnya mendeteksi ini sebagai masalah karena tidak ada implementasi yang valid dalam metode pengujian.

🔍 **Mengapa ini menjadi masalah?**

Jika metode pengujian tidak memiliki implementasi yang jelas, pengujian menjadi tidak bermakna. SonarCloud menyarankan agar kita menambahkan komentar yang menjelaskan alasan metode kosong, melempar `UnsupportedOperationException`, atau menyelesaikan implementasinya agar benar-benar menguji sesuatu.

❌ **Sebelum Perbaikan**
```java
@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testApplicationStarts() {
        EshopApplication.main(new String[] {});
    }
}
```
✅ **Solusi** : Menggunakan `assertThrows()`

Alih-alih membiarkan metode kosong, kita dapat menggunakan `assertThrows()` untuk memastikan bahwa jika ada error saat inisialisasi, pengujian tetap dapat menangkapnya.
```java
import static org.junit.jupiter.api.Assertions.assertThrows;

@Test
void contextLoads() {
    // Menguji apakah aplikasi gagal dijalankan dengan error yang sesuai
    assertThrows(Exception.class, () -> EshopApplication.main(new String[]{}));
}
```

#### 3. **Avoid public Modifier in Test Classes**
📌 **Permasalahan** :

Dalam JUnit 5, test class tidak memerlukan modifier `public`. Penggunaan `public` yang tidak perlu hanya menambah kompleksitas tanpa memberikan manfaat tambahan.

🔍 **Mengapa ini menjadi masalah?**

JUnit 5 bisa menjalankan test tanpa `public`, karena framework ini menggunakan refleksi untuk mengeksekusi metode pengujian. Secara default, method yang ada di dalam _interface_ sudah bersifat `public`, jadi modifier tersebut bisa dihapus untuk membuat kode lebih bersih.
Dengan menghapus `public`, kita dapat menjaga keterbacaan dan konsistensi kode tanpa mengubah fungsionalitasnya.

❌ **Sebelum Perbaikan**
```java
@public class CreateProductFunctionalTest { 
    ...
}
```
✅ **Solusi** : Cukup tulis tanpa `public`
```java
class CreateProductFunctionalTest { 

}
```

#### 4. **Clarify empty method with Comment**
📌 **Permasalahan** :

Metode `setUp()` dalam unit test awalnya kosong tanpa komentar atau implementasi yang jelas, sehingga bisa membingungkan.

🔍 **Mengapa ini menjadi masalah?**

Metode `setUp()` dijalankan sebelum setiap test untuk melakukan inisialisasi. Jika kosong tanpa penjelasan, pengembang lain mungkin tidak tahu apakah memang belum diperlukan atau ada yang terlupakan.

❌ **Sebelum Perbaikan**
```java
@BeforeEach
void setUp() {
    
}
```
✅ **Solusi** : Menambahkan komentar untuk menjelaskan fungsinya meskipun masih kosong
```java
void setUp() {
    // Metode ini dipanggil sebelum setiap test untuk inisialisasi, jika diperlukan.
}
```

### Apakah implementasi CI/CD sudah sesuai dengan definisinya?
Menurut saya, implementasi **CI/CD** dalam proyek ini sudah cukup memenuhi prinsip _Continuous Integration_ dan _Continuous Deployment_. Saya menggunakan **GitHub Actions** untuk menjalankan beberapa **workflow** otomatis seperti `ci.yml`, `scorecard.yml`, dan `sonarcloud.yml`. Dimana setiap kali ada perubahan kode, melalui **push** atau **pull request**, **workflow** ini langsung berjalan otomatis untuk memastikan kode diuji dan dianalisis sebelum digabung ke branch utama. **SonarCloud** juga digunakan untuk mengevaluasi kualitas kode dan mengidentifikasi potensi bug. Untuk **CD** sendiri, saya mengandalkan **Koyeb** sebagai platform deployment otomatis. Setelah kode melewati tahap pengujian dan validasi, aplikasi langsung dideploy tanpa perlu proses manual sehingga dapat dipastikan aplikasi selalu dalam versi terbaru. Dengan **workflow** ini, seluruh proses mulai dari kode, pengujian, review, hingga deployment berjalan otomatis, sehingga lebih efisien dan sesuai dengan prinsip CI/CD.

</details>
<details>
<summary>Module 3: Maintainability & OO Principles</summary>

## Reflection

### Penerapan Prinsip S.O.L.I.D
#### 1. **Single Responsibility Principle (SRP)**
Sebelumnya, `ProductController` menangani semua produk, termasuk ***mobil*** (Car). Ini menyebabkan controller memiliki dua tanggung jawab sekaligus, yaitu mengelola produk umum dan mobil. Dengan memisahkan `CarController` ke dalam file sendiri, kini `ProductController` hanya menangani produk umum, sementara `CarController` fokus pada mobil.

- **HomePageController** menangani `endpoint /`, 
- **ProductController** menangani `/product`,
- **CarController** menangani `/car`. 

Ini membuat kode lebih rapi, modular, dan mudah dikelola.

#### 2. **Liskov Substitution Principle (LSP)**
Jika `CarController` mewarisi `ProductController`, maka ia akan memiliki method-method yang tidak relevan dengan mobil, seperti ***createProduct()*** atau ***editProduct()***. Ini melanggar **LSP** karena objek subclass (`CarController`) tidak bisa menggantikan superclass-nya (`ProductController`) tanpa mengubah perilakunya. Solusinya adalah menghapus _extends_ `ProductController` dan membuat `CarController` berdiri sendiri sehingga masing-masing class memiliki perilaku yang sesuai.

#### 3. **Interface Segregation Principle (ISP)**
`CarService` sendiri sudah menerapkan **ISP** dengan baik karena hanya berisi method yang relevan untuk operasi ***CRUD (Create, Read, Update, Delete)*** mobil, seperti ***create()***, ***findAll()***, dan ***deleteCarById()***. Jika ada fitur tambahan seperti _filtering_ atau _sorting_, kita bisa membuat _interface_ terpisah tanpa mengubah `CarService`. Dengan cara ini, _interface_ tetap sederhana dan tidak membebani class yang mengimplementasikannya dengan method yang tidak relevan.

#### 4. **Dependency Inversion Principle (DIP)**
Sebelumnya, `CarController` bergantung langsung pada `CarServiceImpl`, yaitu implementasi konkret dari `CarService`. Ini melanggar **DIP** karena ketergantungan langsung pada detail implementasi. Setelah diperbaiki, `CarController` sekarang bergantung pada `CarService`, yaitu sebuah _interface_ bukan implementasi langsung. Oleh karena itu, saya mengubah tipe data menjadi `CarService`, sehingga kode lebih fleksibel dan memudahkan penggantian implementasi tanpa perlu mengubah `CarController`.

### Keuntungan menggunakan Prinsip S.O.L.I.D
---

Menurut saya, menggunakan prinsip S.O.L.I.D dalam pengembangan perangkat lunak memberikan banyak keuntungan, terutama dalam hal modularitas, keterbacaan, dan pemeliharaan kode. Dengan kode yang lebih terstruktur, perubahan atau penambahan fitur dapat dilakukan dengan lebih mudah tanpa merusak bagian lain dari sistem. Prinsip seperti ***SRP (Single Responsibility Principle)*** memastikan setiap class hanya memiliki satu tanggung jawab, sehingga kode lebih terorganisir dan tidak bercampur dengan fungsi lain yang tidak relevan. Selain itu, dengan menerapkan ***DIP (Dependency Inversion Principle)*** dan ***ISP (Interface Segregation Principle)***, ketergantungan pada implementasi konkret dapat dikurangi, sehingga memungkinkan fleksibilitas dalam mengganti atau menambah fitur baru tanpa mengubah kode yang sudah ada. 

Salah satu contoh penerapan **SRP** yang paling _simple_ adalah dengan memisahkan `ProductController` dan `CarController`. Sebelumnya, keduanya tergabung dalam satu kelas yang menangani lebih dari satu tanggung jawab, yang melanggar prinsip **SRP**. Namun, dengan pemisahan ini, setiap controller hanya bertanggung jawab atas satu entitas spesifik.
```java
@Controller
@RequestMapping("/product")
public class ProductController {
    // hanya menangani Product
}

@Controller
@RequestMapping("/car")
public class CarController {
    // hanya menangani Car
}

```

### Kekurangan tidak menggunakan Prinsip S.O.L.I.D
---

Tanpa menerapkan prinsip S.O.L.I.D, kode menjadi sulit dipelihara, sulit diperluas, dan rentan terhadap _bug_ karena adanya ketergantungan yang tidak semestinya. 

- Jika **SRP** tidak diterapkan, satu class dapat menangani berbagai hal sekaligus, menyebabkan kode sulit dimengerti dan diubah. Contohnya ketika `CarController` dan `ProductController` digabung menjadi satu class `StoreController`, maka perubahan pada **Car** bisa berdampak pada **Product**, meskipun keduanya tidak berhubungan.
- Selain itu, tanpa **LSP**, subclass dapat memiliki perilaku yang tidak sesuai dengan superclass-nya, seperti jika `CarController` mewarisi `ProductController`, maka akan mendapatkan metode yang tidak relevan dengan **Car**, yang berpotensi menyebabkan _bug_.
- Tanpa **ISP**, sebuah _interface_ bisa memiliki terlalu banyak metode, memaksa class mengimplementasikan metode yang tidak dibutuhkan, misalnya `CarService` harus mengimplementasikan sebuah metode dari `ProductService`, padahal tidak relevan untuk mobil, sehingga memerlukan implementasi kosong atau melempar _UnsupportedOperationException_.
```java
public class CarServiceImpl implements ProductService {
    @Override
    public void create(Product product) {

    @Override
    public void update(Product product) { 

    @Override
    public void delete(String id) { 

    @Override
    public void generateReport(Product product) {
        throw new UnsupportedOperationException("Laporan tidak didukung untuk Car");
    }
}

```
- Jika **DIP** tidak diterapkan, class akan bergantung langsung pada implementasi konkret, bukan pada abstraksi. Misalnya, jika `CarController` langsung bergantung pada `CarServiceImpl` daripada _interface_ `CarService`, maka jika ada perubahan pada implementasi, seluruh class yang bergantung padanya juga harus diubah, yang bertentangan dengan prinsip fleksibilitas dalam pengembangan perangkat lunak.

</details>
