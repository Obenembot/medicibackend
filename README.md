# Medici Service

### Some Checks that are made
1. Checks For fraudulent activities when updating  a user. (By Id and Email)
2. Email Validation to avoid Duplicates
3. Audit History on Use is kept (To See what happened on every user And Who made the changes)
4. Also password is Based64 encoded 
5. Added a new api to update user password. 


### Updates to be made
1. Extra Security is needed e.g Spring Security and keeping of Ip’s
2. Password should be encoded with BCryptPasswordEncoder
3. 