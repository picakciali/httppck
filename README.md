# Httppck- Android REST (JSON) Client 
Httppck, Android uygulamarınızda REST isteklerinizi basit bir şekilde yapabilmek için  tasarlanmıştır, Android SDK içinde olan AsyncTask sınıfını kullanarak arka plan isteklerini yürütür..

Not: Kullanıma başlamadan önce bu kütüphanede JSON formatları için GSON kütüphanesi kullanılmıştır aşağıdaki Repository adresinden android projenize ekleyebilirsiniz

```java
 compile 'com.google.code.gson:gson:2.8.1'
```

# Verilmesi gereken yetkiler
```java
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```  

# Kullanım
Aşağıdaki örnek'de belirlenen url'ye bir post isteği yapılmaktadır, Bu post isteğinde UYE nesnesini seriliaze etmenize gerek yoktur, Kütüphane otamatik olarak seriliaze ederek yapar isteği

```java
Http http = new HttpBuilder().build(context);
http.post("http://xxxx.com/api/users")
    .data(new UYE("Ali","PIÇAKCI"))
    .send();
```

Aşağıdaki örnekte tipik, bir isteğin  işlenmesi gösterilmiştir

```java
String url = "http://xxx.xxx.xxx/api/xxx.php";
 http.post(url).handler(new ResponseHandler<UYE[]>() {
            private ProgressDialog progressDialog;

            @Override
            public void post() {
                //Ui iş parcaccığında işlem bitene kadar kullanıcıya bir 
                //progressDialog gösteriliyor...
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("İşlem yapılıyor lütfen bekleyiniz");
                progressDialog.show();
            }

            @Override
            public void success(UYE[] array, HttpResponse response) {
                //istek yapılan endpoint cevap olarak üyelerin listesini
                //dönmüş ve liste haline getirilmiştir.
                List<UYE> uyeListesi = Arrays.asList(array);t
            }

            @Override
            public void error(String message, HttpResponse response) {
                //sunucudan olabilecek hataların döndüğü durumlarda
                //burada işletilmektedir
                Log.d("Error",message + " " + "\n" + response.getCode());
                Toast.makeText(context,"Bir Hata meydana geldi "+ message
                ,Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void failure(NetworkError error) {
                //ağ hataları burada işletilmektedir
                Toast.makeText(context,"Internet bağlatınız yok," +
                        "Lütfen internet ayarlarınızı kontrol ediniz",
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void complete() {
                //kütüphane ile yapılan istekler ister başarılı ister başarsız olsun 
                //en son complete çağrılmaktadır
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
           
        }).send();
```
#Token Based Authentication özelliği eklenmiştir.
Eğer bir Token Based Authentication  sunucunuz varsa bu kütüphaneyle kolayca implemente edebilirsiniz. Aşağıda nasıl kullanılacağı gösterilmiştir...

Not: grant_type değeri default olarak "password" dur..

```jav
        Credentials credentials =  new Credentials();
        credentials.username = "xxxx";
        credentials.password = "xxxxx";
        credentials.url = "http://www.xxx.com/token";
        Http http = new HttpBuilder()
                    .authType(AuthType.TokenBasedAuthentication)
                    .credentials(credentials)
                    .build(context);

        http.get("http://www.xxx.com/api/getUsers").handler(responsehandler).send()

```  
