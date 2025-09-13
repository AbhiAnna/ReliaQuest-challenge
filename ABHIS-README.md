Had a really fun time working on this project! Implemented a few things that I wanted to explain in case there are any issues.

Backend:
    - Theres a middleware layer for the API's
    - You have to input a header called SECURITY-KEY with value key 
    - Just wanted to show some implementation of how security would work in the application
    - Does not have full functionality, would need a log in page that would create the tokens dynamically and store in a front end cache 

Frontend:
    - Implemented a little react frontend for this application
    - Steps to set up 
        - cd Frontend/employee-ui
        - npm i
        - Check src/app/client.js to ensure correct backend URL is listed 
        - Check in employeeController and webConfig correct frontend URL is listed 
        - npm run dev 
    - Didn't get to work on properly handling errors. It shows 400 errors if you have incorrect inputs, would've liked to implement more clear error displays on frontend. You have to do a bit of guessing if you run into an error when adding an employee. You can look at the checks in the employeeService.

Looking forward to hearing back! 