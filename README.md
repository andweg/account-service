# Account Service
A Spring-based REST service allowing for the delivery of payrolls to employee's accounts on the corporate website.

## Features
* User signup (requires first name, last name, valid corporate e-mail and a password of minimum 12 char length)
* Changing user password
* Displaying user list
* Removing users 
* Granting and removing user roles: regular user (employee), accountant, auditor and administrator; combination of admin & business roles not permitted
* Adding and modifying payrolls for specific employees and periods of time 
* Accessing own payrolls 
* Locking & unlocking user accounts
* Automatic locking of user account after recognizing a brute force attack (5 failed login attempts)
* Logging security events (failed logins, brute force & unauthorized access attempts, user lock/unlock)
* Accessing security logs

## Endpoints
#### accessible to everyone
* ```POST /api/auth/signup``` user signup 
#### registered users
* ```POST /api/auth/changepass``` changes the current user's password
#### employees & accountants
* ```GET /api/empl/payment``` returns the list of all payrolls of the current user
* ```GET /api/empl/payment/?period={mm-YYYY}``` returns the payroll of the current user for the specified month of the year
#### accountants
* ```POST /api/acct/payments``` adds any number of payrolls to the database
* ```PUT /api/acct/payment``` updates a payroll for a particular employee and period
#### auditors
* ```GET /api/security/events``` returns the list of all security events registered in the database
#### administrator
* ```GET /api/admin/user``` returns the list of all registered users 
* ```PUT /api/admin/user/access``` locks and unlocks user accounts
* ```DELETE /api/admin/{user-email}``` removes users from the database
* ```PUT /api/admin/user/role``` grants and removes user roles 

## Sources
This application was created as a graduate project for the [JetBrains Academy](https://www.jetbrains.com/academy/)'s Java Backend track.
