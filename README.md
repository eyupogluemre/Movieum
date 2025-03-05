# MOVIEUM

**FMSS Android Development Bootcamp with Jetpack Bitirme Projesi**

Movieum, FMSS Android Development Bootcamp kapsamında Jetpack bileşenleri kullanılarak geliştirilmiş bir film satın alma uygulamasıdır. Bu uygulama, sunduğu geniş veritabanı ile kullanıcıların filmleri keşfetmelerine ve satın almalarına olanak tanır.

## Ekran Görüntüleri

| ![Google Pixel 2](https://github.com/user-attachments/assets/16398305-ec48-4bf1-b443-d7c7fd10c469) | ![Google Pixel 2 (1)](https://github.com/user-attachments/assets/d8044e05-69a9-4ed7-800b-2872f8e400f3) | ![Google Pixel 2 (2)](https://github.com/user-attachments/assets/fe205966-924a-47b9-800a-c0b13f473e67) |
|-------------------------|-----------------------|-----------------------|

| ![Google Pixel 2 (3)](https://github.com/user-attachments/assets/013a3947-b4be-41d9-99ae-05070dab0c2e) | ![Google Pixel 2 (8)](https://github.com/user-attachments/assets/7c090096-1a86-4352-8704-b861ae1989f7) | ![Google Pixel 2 (4)](https://github.com/user-attachments/assets/bc7b49b9-a558-4025-977a-4eaf71c178c5) |
|-------------------------|-----------------------|-----------------------|

| ![Google Pixel 2 (5)](https://github.com/user-attachments/assets/4f261260-b570-4e8d-9315-2b7cedd52e24) | ![Google Pixel 2 (6)](https://github.com/user-attachments/assets/8d8c1fdb-5221-4208-bbcf-4667c4cc675c) | ![Google Pixel 2 (7)](https://github.com/user-attachments/assets/91abca2c-5531-46c9-9476-1bd3e915163c) |
|-------------------------|-----------------------|-----------------------|

## Özellikler

- **Film Keşfi**: Kullanıcılar, kategorilerine göre tüm filmleri keşfedebilirler.
- **Film Detayları**: Her film için detaylı bilgi sayfası bulunmaktadır.
- **Favoriler**: Kullanıcılar, favori filmlerini işaretleyebilirler.
- **Arama**: Belirli bir filme göre arama yapma imkanı.
- **Kaydolma, Kimlik Doğrulama, Giriş-Çıkış Yapma, Şifre Sıfırlama**: Kullanıcılar, Firebase entegrasyonu ile kaydolma, kimlik doğrulama, giriş-çıkış yapma ve şifre sıfırlama işlemlerini gerçekleştirebilirler.
- **Kupon Kodu Oluşturma ve Kullanma**: Kullanıcılar, "Kupon" ekranı aracılığıyla çark çevirerek kazandıkları kupon kodunu "Sepet" sayfasında uygulayarak indirim kazanabilirler.

## Kullanılan Teknolojiler

- **Kotlin**: Uygulamanın ana programlama dili olarak kullanılmıştır.
- **Jetpack Compose**: Modern Android uygulama geliştirme için gerekli bileşenler kullanılmıştır.
- **MVVM Mimarisi**: Uygulama, Model-View-ViewModel mimarisi ile yapılandırılmıştır.
- **Retrofit**: API istekleri için kullanılmıştır.
- **Hilt**: Bağımlılık yönetimi için entegre edilmiştir.
- **LiveData ve Coroutine**: UI verilerinin yönetimi ve güncellenmesi için kullanılmıştır.
- **Lottie**: Kupon ekranındaki çark animasyonu için kullanılmıştır.
- **Glide**: Film afişlerinin gösterimi için kullanılmıştır.
- **Firebase**: Kullanıcı kaydolma, kimlik doğrulama, giriş-çıkış yapma ve şifre sıfırlama için kullanılmıştır.

## API

Bu proje, film verilerini almak ve alışveriş sepeti işlemlerini yönetmek için aşağıdaki API'leri kullanır:

- **Get All Movies**: [http://kasimadalan.pe.hu/movies/getAllMovies.php](http://kasimadalan.pe.hu/movies/getAllMovies.php)
- **Get Cart Movies**: [http://kasimadalan.pe.hu/movies/getMovieCart.php](http://kasimadalan.pe.hu/movies/getMovieCart.php)
- **Add Movie to Cart**: [http://kasimadalan.pe.hu/movies/insertMovie.php](http://kasimadalan.pe.hu/movies/insertMovie.php)
- **Delete Movie from Cart**: [http://kasimadalan.pe.hu/movies/deleteMovie.php](http://kasimadalan.pe.hu/movies/deleteMovie.php)
- **Fetch Movie Images**: [http://kasimadalan.pe.hu/movies/images](http://kasimadalan.pe.hu/movies/images)

## Kurulum

1. **Depoyu Klonlayın**:

   ```bash
   git clone https://github.com/eyupogluemre/Movieum.git
   ```

2. **Android Studio ile Açın**: Klonladığınız projeyi Android Studio'da açın.

3. **Gerekli Bağımlılıkları Yükleyin**: Proje açıldığında, gerekli tüm bağımlılıklar otomatik olarak yüklenecektir.

4. **Uygulamayı Çalıştırın**: Projeyi derleyin ve emülatör veya fiziksel cihazda çalıştırın.

---

Herhangi bir sorunuz veya öneriniz varsa benimle iletişime geçebilirsiniz! 😎
