# Project Notes

Had a really fun time working on this project! Implemented a few things that I wanted to explain in case there are any issues.

## Backend
- There’s a middleware layer for the APIs.  
- You have to input a header called `SECURITY-KEY` with value `key`.  
- Just wanted to show some implementation of how security would work in the application.  
- Does not have full functionality: would need a login page that would create the tokens dynamically and store them in a front-end cache.  

## Frontend
- Implemented a small React frontend for this application.  

### Steps to Set Up
1. `cd Frontend/employee-ui`
2. `npm i`
3. Check `src/app/client.js` to ensure the correct backend URL is listed.  
4. Check in `EmployeeController` and `WebConfig` that the correct frontend URL is listed.  
5. `npm run dev`  

### Notes
- Didn’t get to work on properly handling errors on frontend.  
- It shows 400 errors if you have incorrect inputs.  
- Would’ve liked to implement clearer error displays on the frontend.  
- Right now, you have to do a bit of guessing if you run into an error when adding an employee.  
- You can look at the checks in the EmployeeService for details.  

---

Looking forward to hearing back!
