# Account Service
#### A Spring-based REST service allowing for the delivery of payrolls to employee's accounts on the corporate website.

## Endpoints
#### available to everyone:
* ```POST /api/auth/signup``` user signup (requires first name, last name, valid corporate e-mail and a password of minimum 12 char length)
#### registered users:
* ```POST /api/auth/changepass``` changes the current user's password
#### employees & accountants:
* ```GET /api/empl/payment``` returns the list of all payrolls of the current user
* ```GET /api/empl/payment/?period={mm-YYYY}``` returns the payroll of the current user for the specified month of the year
#### accountants:
* ```POST /api/acct/payments``` adds any number of payrolls to the database
* ```PUT /api/acct/payment``` updates a payroll for a particular employee and period
#### auditors:
* ```GET /api/security/events``` returns the list of all security events (failed logins, brute force & unauthorized access attempts, user lock/unlock) registered in the database
#### administrator:
* ```GET /api/admin/user``` returns the list of all registered users (with user information, including singup data and all roles)
* ```PUT /api/admin/user/access``` locks and unlocks user accounts
* ```DELETE /api/admin/{user-email}``` removes users from the database
* ```PUT /api/admin/user/role``` grants and removes user roles (does not allow for combination of admin and business roles)
