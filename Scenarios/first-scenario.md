# Implementation First Scenario

## The Actors :

- **Admin**
- **Agent**
- **Tourist**

## Scenario

- **The main page** will contain an welcome text `Welocme To Smart Tourist Guide` and two buttons one for `Login`, and the other for `SignUp`. If click on `SignUp`, the tourist sign up page will appear. Else if click on login should at begin select one of radibuttons `Agent`, `Admin`, `Tourist`, then the edittexts will appear. Fill information and click on login button.

- **Sigun up page** will ask user to enter his information, name and age, sex, narionality, email and password. Then will check     
    - if the user exists or not check (username, email).

    - else the user will added to database, then succecfull msg will appear `Sign up Complete Succesfull`.

- **Login Page** ask user to choose his role (`Admin`, `Agent`, `Tourist`), then fill two text field the first one enter `username` and `password`. On Click the `Login` button the validation will start get the input information to validate it with firebase.

    - On login not success, the toast massege will appear with msg `The login Failed, Please check info and try again!`.
    - On login succesfull , toast massege will appear with msg `Succesfull Login`
        - if role was `Admin`, the admin main page will open.
        - if role was `Agent`, the agent home page will appear.
        - if role was `Tourist`, the tourist main page will appear.




