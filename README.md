# Httppck- Android REST (JSON) Client 
Httppck, Android uygulamarınızda REST isteklerinizi basit bir şekilde yapabilmek için  tasarlanmıştır, Android SDK içinde olan AsyncTask sınıfını kullanarak REST uygulamarınızda Http işleminiz sonuclana kadar isterseniz kullanıcıya bir bilgilendirme ekranı gösterebilirsiniz..

Not: Kullanıma başlamadan önce bu kütüphanede JSON formatları için GSON kütüphanesi kullanılmıştır aşağıdaki Repository adresinden android projenize ekleyebilirsiniz

```java
 compile 'com.google.code.gson:gson:2.8.1'
```

# Kullanım
Aşağıdaki örnek belirlelen url'ye UYE sınıfı Json formatında gönderecektirr

```java
Http http = new HttpFactory().create(context);
http.post("http://xxxx.com/api/users")
    .data(new UYE("Ali","PIÇAKCI"))
    .send();
```

Eğer işlem bittikden sonra bir geri bildirim istiyorsanız aşağıdaki örnek gibi olacaktır...

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
                //Sunucudan gelen json GSON tarafından bir java array nesnesine
                //dönüştürüldü, ve sonra burada, List<UYE> nesnesine dönderildi
                List<UYE> uyeListesi = Arrays.asList(array);
            }

            @Override
            public void error(String message, HttpResponse response) {
                //Olası bir sunucu hatası durumunda bu method çalışır
                Log.d("Error",message + " " + "\n" + response.getCode());
                Toast.makeText(context,"Bir Hata meydana geldi "+ message
                ,Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void failure(NetworkError error) {
                //Kullanıcının networku yoksa yani interneti  bu methot çalışır
                Toast.makeText(context,"Internet bağlatınız yok," +
                        "Lütfen internet ayarlarınızı kontrol ediniz",
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void complete() {
                //istek ister başarılı ister başarsız olsun en son bu method calısır
                //ve kullanıcı ekranında acık olan progressDialog kapatılır
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
           
        }).send();
```
#Token Based Authentication özelliği eklenmiştir.
Eğer bir Token Based Authentication  sunucunuz varsa bu kütüphaneyle kolayca implemente edebilirsiniz. Aşağıda nasıl kullanılacağı gösterilmiştir...

Not: grant_type değeri default olarak "password" dur..

```java
        AuthReseource auth =  new AuthReseource();
        auth.username = "xxxx";
        auth.password = "xxxxx";
        auth.type = AuthType.BasedAuthentication;
        auth.url = "http://www.xxx.com/token";
        HttpFactory factory  =  new HttpFactory(HttpFactory.AUTH);
        factory.setAuthReseource(auth);
        Http http =  factory.create(context);
        
        http.get("http://www.xxx.com/api/getUsers").handler(responsehandler).send()

```  
