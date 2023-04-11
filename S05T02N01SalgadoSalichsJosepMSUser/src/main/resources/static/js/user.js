/*!
  * IT Academy Backend JAVA S05T02N01
  * Author  => Josep Salgado Salichs
  *
  */

//#region VARIABLES
var tokenVar = "";
var editNameEnableGlb = false;
var resultGl = "";

//#endregion VARIABLES


//#region OPEN WINDOWS


function OpenGame(nameIn) {
  if (nameIn === '') {
    nameIn = GetUserName();
  }
  window.open("http://localhost:9011/game/gameFrm/" + nameIn, "_self");

}

//#endregion OPEN WINDOWS


//#region MAIN FUNCTIONS
function SignUpFunc() {
  // ATTRIBUTES
  let currentDate = new Date().toJSON().slice(0, 10);
  var result = "";

  // TAKEN INFO FROM DOCUMENT
  let userName = document.getElementById("userName").value;
  let pass = document.getElementById("pass1").value;
  let pass2 = document.getElementById("pass2").value;

  // INITIAL CHECKS
  if (userName == "") {
    alert("User name isn't empty!");
  } else if (!checkPass(pass, pass2)) {
    alert("The password isn't correct!");
  } else {
    // CALL API TO CREATED NEW USER
    result = register(userName, pass, currentDate);

    // CHECK API RESULT
    if (result !== undefined) {
      if (result === "OK") {
        // User created correctly, can jump to game pag
        openGame(userName);

      } else {
        // Some error occurred
        SomeErrorOccurredSignUp(result);

      }
    }
  }
}

function SignInFunc() {
  // ATTRIBUTES
  var result = "";

  // TAKEN INFO FROM DOCUMENT
  let userName = document.getElementById("userName").value;
  let pass = document.getElementById("pass").value;

  // INITIAL CHECKS
  if (userName === "") {
    alert("User name isn't empty!");
  } else if (pass === "") {
    alert("password isn't empty!");
  } else {
    // CHECK IF USER EXIST IN SYSTEM
    result = login(userName, pass);

    if (result !== undefined) {
      // CHECK API RESULT  
      if (result.length !== "OK") {
        // Some error occurred
        SomeErrorOccurredSignUp(result);

      } else {
        // User created correctly, can jump to game pag
        openGame(userName);

      }
    }
  }
}

function UserViewOnload() {
  // VARIABLES
  editNameEnableGlb = false;

  // UPDATE ALL VIEW EWLEMENTS
  document.getElementById('DateSignUpView').value = GetUserDataSignUp();
  document.getElementById('UserNameView').value = GetUserName();
  document.getElementById('PasswordView').value = "*******";

}

async function UserViewUpdateName() {
  // VARIABLES
  let newUserName = "";
  let actualUserName = "";

  // Get actual userName
  actualUserName = GetUserName();
  // Get new userName
  newUserName = document.getElementById('UserNameView').value;
  console.log(actualUserName);
  console.log(newUserName);

  // Initial checks
  if (newUserName === "") {
    alert("New user name can't be empty!\nPlease try again, really you can.");
  } else if (actualUserName === newUserName) {
    alert("New user name and actual user name, are the same!\nPlease try again, really you can.");
  } else {
    // Call function to update user name
    await updateUserName(newUserName, actualUserName);

  }
}

function checkPass(pass, pass2) {
  let resul = false;

  if (pass === pass2) {
    resul = true;
  } else {
    resul = false;
  }

  return resul;

}

//#endregion MAIN FUNCTIONS


//#region AUXILIAR FUNCTIONS
function checkUser(nameIn, passIn) {
  var result = "";
  var request = new XMLHttpRequest();
  var url = 'http://localhost:9011/user/check';
  let data = '{ "userName": "' + nameIn + '", "pass": "' + passIn + '", "dateSignUp": "" }';

  request.open("GET", url, true);

  request.setRequestHeader("Content-Type", "application/json");
  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        // OK
        result = "OK";
      } else if (request.status === 409) {
        // Conflict
        result = request.responseText;
      } else if (request.status === 500) {
        // INTERNAL_SERVER_ERROR
        result = request.responseText;
      } else {
        result = "Error Unexpected";
      }

      return result;
    }

  }

  request.send(data);

}

function login(nameIn, passIn) {
  // VARIABLES
  var result = "";
  var request = new XMLHttpRequest();
  var url = 'http://localhost:9011/user/auth/login';
  var token = "";

  // GENERATE USER STRUCUTRE
  let data = '{ "userName": "' + nameIn + '", "pass": "' + passIn + '" }';

  // GENERATE REQUEST
  request.open("POST", url, true);
  request.setRequestHeader("Content-Type", "application/json");
  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        // OK
        result = "OK";

        // Take info recived
        var objJSON = JSON.parse(request.responseText);

        // Save userInfo
        SaveToken(objJSON["accessToken"]);
        SaveBasicUserInfo(nameIn, "");

        // Open game View
        OpenGame(nameIn);
      } else if (request.status === 409) {
        // Conflict
        result = request.responseText;
        SomeErrorOccurredSignUp(result);
      } else if (request.status === 500) {
        // INTERNAL_SERVER_ERROR
        result = request.responseText;
        SomeErrorOccurredSignUp(result);
      } else {
        result = "Error Unexpected";
        SomeErrorOccurredSignUp(result);
      }

      return result;
    }
  }

  request.send(data);

}

function register(nameIn, passIn, dataSignUpIn) {
  // VARIABLES
  var result = "";
  const request = new XMLHttpRequest();
  const url = 'http://localhost:9011/user/auth/register';
  var token = "";

  // GENERATE USER STRUCUTRE
  let data = '{ "userName": "' + nameIn + '", "pass": "' + passIn + '", "dateSignUp": "' + dataSignUpIn + '" }';

  // GENERATE REQUEST
  request.open("POST", url, true);
  request.setRequestHeader("Content-Type", "application/json");
  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        result = "OK";

        // Take token
        var objJSON = JSON.parse(request.responseText);

        // Save user Info
        SaveToken(objJSON["accessToken"]);
        SaveBasicUserInfo(nameIn, dataSignUpIn);

        // Open game View
        OpenGame(nameIn);
      } else if (request.status === 400) {
        // BAD_REQUEST
        result = request.responseText;
        SomeErrorOccurredSignUp(result);
      } else if (request.status === 500) {
        // INTERNAL_SERVER_ERROR
        result = request.responseText;
        SomeErrorOccurredSignUp(result);
      } else {
        result = "Error Unexpected"
        SomeErrorOccurredSignUp(result);
      }

      return result;
    }

  }

  request.send(data);

}

function SomeErrorOccurredSignUp(textIn) {
  if (textIn.includes("Some error occurred when to save")) {
    alert("An error occurred when to save you info\nPlease try later.")

  } else if (textIn.includes("User already exis")) {
    alert("This user name alredy exist on the system\nPlease try with another user name.")

  } else if (textIn.includes("An unexpected error occurred")) {
    alert("An unexpected error occurred\nPlease try later.")

  } else {
    alert(textIn);
  }

}

function updateUserName(newUserNameIn, actualUserNameIn) {
  // VARIABLES
  let result = "";
  let request = new XMLHttpRequest();
  let url = '';
  let token = "";
  let data ='{ "userName": "", "pass": "" }';

  // GET USER INFO
  token = GetToken();

  // GENERATE URL
  url = 'http://localhost:9011/user/' + actualUserNameIn + '/' + newUserNameIn + '/update';

  // GENERATE REQUEST
  request.open("PUT", url, false);
  request.setRequestHeader("Autorization", "Bearer " + token);
  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        // Update username
        SaveBasicUserInfo(newUserNameIn, "");
        alert("User name modify successful!!");
        return;

      } else if (request.status === 400) {
        // BAD_REQUEST
        result = request.responseText;
        SomeErrorOccurredSignUp(result);
        resultGl = result;
        return;

      } else if (request.status === 500) {
        // INTERNAL_SERVER_ERROR
        result = request.responseText;
        SomeErrorOccurredSignUp(result);
        resultGl = result;
        return;

      } else {
        result = "Error Unexpected"
        SomeErrorOccurredSignUp(result);
        resultGl = result;
        return;

      }
    }
  }

  // SEND REQUEST
  request.send(data);

}

//#endregion AUXILIAR FUNCTIONS


