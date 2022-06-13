# virtual-library app
OpenSource API for Trainning the Front end of application. Developed using the Angular 14, Angular Material and TypeScript Language.

The app has deployed in AWS Service, using a S3 Bucket to host a Web Static Page, with the endpoint request to backend deployed in AWS Service EC2.

## Test APP
There are 2 ways to test the application

1. **In AWS with public ip**
  * Link to access the virtual-library app: "http://virtual-library-angular.s3-website-us-east-1.amazonaws.com/login"
  * Choose one of the users from the "Test Users" field (Becareful with permissions roles)

2. **In LocalHost**
  * Clone the repository in a folder
  * Install the dependencies (npm install)
  * Access your "http://localhost:4200/"
  * Choose one of the users from the "Test Users" field (Becareful with permissions roles)

## Considerations
The front end is still under development, and the frontend permissions validations and the intuitive error messages captured from the backend are missing.

**A list of what still needs to be developed on the front**
- Validations of Permissions in frontend
- Error Message if the request is failed
- Create User
- Delete User
- Update User
- Create Book
- Delete Book
- Rent Book
- Refund Book
- Token timeout redirect to login

## Users to Test:
1. Lucas -> (email: 'lucas@library.com', password: '789456') - Permission Role: Role Common User
2. Luan -> (email: 'luan@library.com', password: 'luan7845658') - Permission Role: Role Admin
3. Anonymous -> (email: 'any@library.com', password: 'any') - Permission Role: Role Viewer
