to launch this project be sure to have a database located at this location:
``` 
“localhost:3306/leagueApplication”.
```

otherwise modify this line in application.yml:
``` 
url: jdbc:mysql://localhost:3306/leagueApplication
```

also check your phpmyadmin login and modify the two lines that always follow in application.yml:
```
    username: root
    password:
```