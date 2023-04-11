/*!
  * IT Academy Backend JAVA S05T02N01
  * Author  => Josep Salgado Salichs
  *
  */

//#region CONSTANTS
victorysText = "Victories: ";
playsText = "Plays: "
resultText = "RESULT: ";

//#endregion CONSTANTS


//#region VARIABLES
var token = "";
var resultGl = "";
var victorx100Var = 0;
var playsGlblVar = 0;
var wonPlaysGlblVar = 0;
var x100AllUsersVar = "";
var winnerUserVar = "";
var lossUserVar = "";

//#endregion VARIABLES


//#region OPEN WINDOWS
function OpenShowData() {
  window.open("http://localhost:9011/user/auth/loginFrm", "_self");
}

//#endregion OPEN WINDOWS


//#region MAIN FUNCTIONS
async function GameViewOnload() {
  // VARIABLES
  var result = "";

  // Reset all View elements
  document.getElementById('victorx100Var').innerHTML = victorysText.concat("---");
  document.getElementById('playsVar').innerHTML = playsText.concat("---");
  document.getElementById('resultVar').innerHTML = resultText.concat("----");
  document.getElementById('textResultVar').innerHTML = "";
  document.getElementById('rankingAllUsers').innerHTML = "----%";

  // Get plays & % victories of user.
  await GetUserInfo();
  if (resultGl === "OK") {
    victorx100Var = Getx100Vict();
    wonPlaysGlblVar = GetWonPlays();
    playsGlblVar = GetTotalPlays();
    document.getElementById('victorx100Var').innerHTML = victorysText.concat(Math.floor(victorx100Var), " %");
    document.getElementById('playsVar').innerHTML = playsText.concat(playsGlblVar);

    // Load tablePlays values
    await UpdateTablePlays();

    // Load table users
    await UpdateTableUsers();

    // Load ranking users
    await GetUsersRanking();

    // Load winner & loser users
    GetLoserUsers();
    GetWinnerUsers()

  } else {
    //todo Fer alguna cosa si hi ha error
  }

}

async function MixDice() {
  var imgDau = document.getElementById('imageDau1');
  let num1 = 0;
  let num2 = 0;
  let result = 0;
  let interval1 = 100;
  let text1 = 'http://localhost:9011/images/dice/dau_';

  document.getElementById('resultVar').innerHTML = resultText.concat("----");
  document.getElementById('textResultVar').innerHTML = "----";

  for (let i = 0; i < 30; i++) {
    num1 = Math.floor((Math.random() * 6) + 1);
    num2 = Math.floor((Math.random() * 6) + 1);

    // Created variable interval
    if (i > 22) {
      interval1 = 250;
    } else if (i < 10) {
      interval1 = 50;
    } else {
      interval1 = 100;
    }
    await sleep(interval1);

    // Send new image to dice zone
    document.getElementById('imageDice1').setAttribute("src", text1.concat(num1, ".png"));
    document.getElementById('imageDice2').src = text1.concat(num2, ".png");

  }

  // UPDATE VALUES ON VIEW
  if ((num1 + num2) == 7) {
    document.getElementById('textResultVar').innerHTML = "YOU WIN!!";
    wonPlaysGlblVar++;
  } else {
    document.getElementById('textResultVar').innerHTML = "YOU LOSE!!";
  }
  playsGlblVar++;
  victorx100Var = Math.floor(100 * (wonPlaysGlblVar / playsGlblVar));
  document.getElementById('victorx100Var').innerHTML = victorysText.concat(victorx100Var, " %");
  document.getElementById('playsVar').innerHTML = playsText.concat(playsGlblVar);
  document.getElementById('resultVar').innerHTML = resultText.concat(num1 + num2);

  // SAVE IN LOCALSTORAGE VALUES
  SaveCompletUserInfo("", "", victorx100Var, playsGlblVar, wonPlaysGlblVar);

  // REQUEST TO SAVE PLAY
  await SavePlay(playsGlblVar, num1, num2)

  // REQUEST TO UPDATE USER INFO
  await UpdateUserValues();

  // UPDATE TABLE PLAYS
  await UpdateTablePlays();

  // UPDATE USERS RANKING
  await GetUsersRanking();

  // Load winner & loser users
  GetLoserUsers();
  GetWinnerUsers()

}

async function UpdateTablePlays() {
  await UpdateTablePlays2();

}

async function DeleteAllPlays() {
  // VARIABLES
  let token = "";
  let userName = "";

  // TAKE USER INFO
  userName = GetUserName();
  token = GetToken();

  // CALL ENDPOINT TO DELETE USER PLAYS
  await CallEndPointDeletPlyas(userName, token);
  if (resultGl !== "OK") {
    alert("Is impossible delete your plays now, please try later.")
  } else {
    // UPDATE LOCALSTORAGE USER INFO
    await SaveCompletUserInfo("", "", 0, 0, 0);

    // UPDATE USER INFO
    playsGlblVar = 0;
    wonPlaysGlblVar = 0;
    await UpdateUserValues();

    // UPDATE VIEW ELEMENTS
    document.getElementById('victorx100Var').innerHTML = victorysText.concat("0 %");
    document.getElementById('playsVar').innerHTML = playsText.concat("0");

    // UPDATE TABLE PLAYS
    await UpdateTablePlays();

    // UPDATE TABLE USERS
    await UpdateTableUsers();

    // UPDATE USERS RANKING
    await GetUsersRanking();

    // UPDATE WINNER & LOSER USERS
    GetLoserUsers();
    GetWinnerUsers()

  }
}

//#endregion MAIN FUNCTIONS


//#region AUXILIAR FUNCTIONS
function sleep(ms) {
  return new Promise(resolveFunc => setTimeout(resolveFunc, ms));
}

function GetUserInfo() {
  // VARIABLES
  let result = "";
  let request = new XMLHttpRequest();
  let url = 'http://localhost:9011/user/getComplet';
  let userName = "";
  let pass2 = "";
  let token = "";
  let x100Vict = 0;

  // GET USER INFO
  userName = GetUserName();
  token = GetToken();

  // GENERATE USER STRUCUTRE
  let data = '{"id": "0", "userName": "' + userName + '", "pass": "", "dateSignUp": "" }';

  // GENERATE REQUEST
  request.open("POST", url, false);
  request.setRequestHeader("Content-Type", "application/json");
  request.setRequestHeader("Autorization", "Bearer " + token);
  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        result = "OK";

        // Take info recived
        var objJSON = JSON.parse(request.responseText);

        // Save user Info
        if (objJSON["totalPlays"] === 0) {
          x100Vict = 0;
        } else {
          x100Vict = (objJSON["wonPlays"] / objJSON["totalPlays"]) * 100;
        }
        SaveCompletUserInfo("", objJSON["dateSignUp"], x100Vict, objJSON["totalPlays"], objJSON["wonPlays"]);

        resultGl = "OK";

      } else if (request.status === 400) {
        // BAD_REQUEST
        result = request.responseText;
        SomeErrorOccurredSignUp(result);

        resultGl = result;
        return result;
      } else if (request.status === 500) {
        // INTERNAL_SERVER_ERROR
        result = request.responseText;
        SomeErrorOccurredSignUp(result);
        resultGl = result;
        return result;
      } else {
        result = "Error Unexpected"
        SomeErrorOccurredSignUp(result);
        resultGl = result;

      }
      return result;
    }

  }

  // SEND REQUEST
  request.send(data);

}

function SavePlay(playsIn, diceValue1In, diceValue2In) {
    // VARIABLES
    let result = "";
    let token = "";
    let userName = "";
    let request = new XMLHttpRequest();
    let url = ''; ////* 'http://localhost:9011/game/add';

    // TAKE USER INFO
    userName = GetUserName();
    token = GetToken();

    // GENERATE URL
    if (GetDDBBType() === "mysql"){
        url = 'http://localhost:9011/game/add';
    }else{
        url = 'http://localhost:9011/game/mongo/add';
    }

    // GENERATE PLAY STRUCUTRE
    let data = '{ "userName": "' + userName + '", "playNum": "' + playsIn + '", "dice1Value": "' + diceValue1In +
    '", "dice2Value": "' + diceValue2In + '" }';

    // GENERATE REQUEST
    request.open("POST", url, true);
    request.setRequestHeader("Content-Type", "application/json");
    request.setRequestHeader("Autorization", "Bearer " + token);
    request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        // Ok
        result = "OK";

      } else if (request.status === 409) {
        // CONFLICT
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

    // SEND REQUEST
    request.send(data);

}

function UpdateUserValues() {
  // VARIABLES
  var result = "";
  var token = "";
  var userName = "";
  const request = new XMLHttpRequest();
  const url = 'http://localhost:9011/user/update';

  // TAKE USER INFO
  userName = GetUserName();
  token = GetToken();

  // GENERATE PLAY STRUCUTRE
  let data = '{ "userName": "' + userName + '", "dateSignUp": "", "wonPlays": ' + wonPlaysGlblVar +
    ', "totalPlays": ' + playsGlblVar + ' }';

  // GENERATE REQUEST
  request.open("PUT", url, true);
  request.setRequestHeader("Content-Type", "application/json");
  request.setRequestHeader("Autorization", "Bearer " + token);
  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        // Ok
        result = "OK";

      } else if (request.status === 409) {
        // CONFLICT
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

  // SEND REQUEST
  request.send(data);

}

function CallEndPointDeletPlyas() {
  // VARIABLES
  let request = new XMLHttpRequest();
  let url = "";
  let token = "";
  let userName = "";

  // GET USER INFO
  userName = GetUserName();
  token = GetToken();

  // GENERATE URL
  if (GetDDBBType() === "mysql"){
      url = 'http://localhost:9011/game/'+ userName + '/plays';
  }else{
      url = 'http://localhost:9011/game/mongo/'+ userName + '/plays';
  }

  // GENERATE REQUEST
  request.open("DELETE", url, false);
  request.setRequestHeader("Autorization", "Bearer " + token);
  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        resultGl = "OK";

      } else if (request.status === 400) {
        // BAD_REQUEST
        resultGl = request.responseText;
        SomeErrorOccurredSignUp(resultGl);

      } else if (request.status === 500) {
        // INTERNAL_SERVER_ERROR
        resultGl = request.responseText;
        SomeErrorOccurredSignUp(resultGl);

      } else {
        resultGl = "Error Unexpected";
        SomeErrorOccurredSignUp(resultGl);

      }
    }
  }

  // SEND REQUEST
  request.send();
}

function UpdateTablePlays2() {
  // VARIABLES
  let request = new XMLHttpRequest();
  let url = '';
  let token = "";
  let userName = "";

  // GET USER INFO
  userName = GetUserName();
  token = GetToken();

  // GENERATE USER STRUCUTRE
  var data = '{"id": "0", "userName": "' + userName + '", "pass": "", "dateSignUp": "" }';

    // GENERATE URL
    if (GetDDBBType() === "mysql"){
        url = 'http://localhost:9011/game/'+ userName + '/plays';
    }else{
        url = 'http://localhost:9011/game/mongo/'+ userName + '/plays';
    }

  // GENERATE REQUEST
  request.open("GET", url, false);
  request.setRequestHeader("Content-Type", "application/json");
  request.setRequestHeader("Autorization", "Bearer " + token);
  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        // Take info recived
        let objJSON = JSON.parse(request.responseText);

        // ADD ELEMENTS
        let table = '<table class="table table-dark table-striped-columns" id="TablePlaysViewGame">';
        table += '<thead class="text-center">' +
          '<th scope="col">PLAY</th>' +
          '<th scope="col">DICE 1</th>' +
          '<th scope="col">DICE 2</th>' +
          '<th scope="col">RESULT</th>' +
          '<th scope="col">VICT</th>' +
          '</thead>';
        table += '<tbody class="text-center">';
        for (var i = objJSON.length - 1; i >= 0; i--) {
          let play = objJSON[i];
          let resultSum = play.dice1Value + play.dice2Value;
          let vict = "NO";
          if (resultSum === 7) {
            vict = "SI";
          }
          table += '<tr>' +
            '<td>' + play.playNum +
            '</td><td>' + play.dice1Value +
            '</td><td>' + play.dice2Value +
            '</td><td>' + resultSum +
            '</td><td>' + vict +
            '</td></tr>';
        }
        table += "</tbody></table>";

        // Update view
        document.getElementById("TablePlaysViewGame").innerHTML = table;

      } else if (request.status === 400) {
        // BAD_REQUEST
        SomeErrorOccurredSignUp(request.responseText);

      } else if (request.status === 500) {
        // INTERNAL_SERVER_ERROR
        SomeErrorOccurredSignUp(request.responseText);

      } else {
        SomeErrorOccurredSignUp("Error Unexpected");

      }
    }
  }

  // SEND REQUEST
  request.send(data);

}

function UpdateTableUsers() {
  // VARIABLES
  let request = new XMLHttpRequest();
  let url = 'http://localhost:9011/user/getAll';
  let token = "";

  // GET USER INFO
  token = GetToken();

  // GENERATE REQUEST
  request.open("GET", url, false);
  request.setRequestHeader("Autorization", "Bearer " + token);
  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        // Take info recived
        let objJSON = JSON.parse(request.responseText);

        // ADD ELEMENTS
        let table = '<table class="table table-dark table-striped-columns" id="TableUsers">';
        table += '<thead class="text-center">' +
          '<th scope="col">NAME</th>' +
          '<th scope="col">PLAYS</th>' +
          '<th scope="col">% VIC</th>' +
          '</thead>';
        table += '<tbody class="text-center">';
        for (var i = 0; i < objJSON.length; i++) {
          let user = objJSON[i];
          table += '<tr>' +
            '</td><td>' + user.userName +
            '</td><td>' + user.totalPlays +
            '</td><td>' + user.x100Vict + "%" +
            '</td></tr>';
        }
        table += "</tbody></table>";
        // Update view
        document.getElementById("TableUsers").innerHTML = table;

      } else if (request.status === 400) {
        // BAD_REQUEST
        SomeErrorOccurredSignUp(request.responseText);

      } else if (request.status === 500) {
        // INTERNAL_SERVER_ERROR
        SomeErrorOccurredSignUp(request.responseText);

      } else {
        SomeErrorOccurredSignUp("Error Unexpected");

      }
    }
  }

  // SEND REQUEST
  request.send();

}

function GetUsersRanking() {
  // VARIABLES
  let result = "";
  let request = new XMLHttpRequest();
  let url = 'http://localhost:9011/user/ranking';
  let token = "";

  // GET USER INFO
  token = GetToken();

  // GENERATE REQUEST
  request.open("GET", url, false);
  request.setRequestHeader("Autorization", "Bearer " + token);
  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        // Update view info
        document.getElementById('rankingAllUsers').innerHTML = "All Users: " + request.responseText + "%";
        resultGl = "OK";

      } else if (request.status === 400) {
        // BAD_REQUEST
        result = request.responseText;
        SomeErrorOccurredSignUp(result);
        resultGl = result;

      } else if (request.status === 500) {
        // INTERNAL_SERVER_ERROR
        result = request.responseText;
        SomeErrorOccurredSignUp(result);
        resultGl = result;

      } else {
        result = "Error Unexpected"
        SomeErrorOccurredSignUp(result);
        resultGl = result;

      }
    }
  }

  // SEND REQUEST
  request.send();

}

function GetLoserUsers() {
  // VARIABLES
  let request = new XMLHttpRequest();
  let url = 'http://localhost:9011/user/ranking/loser';
  let token = "";

  // GET USER INFO
  token = GetToken();

  // GENERATE REQUEST
  request.open("GET", url, false);
  request.setRequestHeader("Autorization", "Bearer " + token);
  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        // Update view info
        document.getElementById('lossUserVar').innerHTML = "Loser: " + request.responseText;
        resultGl = "OK";

      } else if (request.status === 400) {
        // BAD_REQUEST
        SomeErrorOccurredSignUp(request.responseText);

      } else if (request.status === 500) {
        // INTERNAL_SERVER_ERROR
        SomeErrorOccurredSignUp(request.responseText);

      } else {
        SomeErrorOccurredSignUp("Error Unexpected");

      }
    }
  }

  // SEND REQUEST
  request.send();

}

function GetWinnerUsers() {
  // VARIABLES
  let result = "";
  let request = new XMLHttpRequest();
  let url = 'http://localhost:9011/user/ranking/winner';
  let userName = "";
  let token = "";

  // GET USER INFO
  token = GetToken();

  // GENERATE REQUEST
  request.open("GET", url, false);
  request.setRequestHeader("Autorization", "Bearer " + token);
  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        // Update view info
        document.getElementById('winnerUserVar').innerHTML = "Winner: " + request.responseText;
        resultGl = "OK";

      } else if (request.status === 400) {
        // BAD_REQUEST
        SomeErrorOccurredSignUp(request.responseText);

      } else if (request.status === 500) {
        // INTERNAL_SERVER_ERROR
        SomeErrorOccurredSignUp(request.responseText);

      } else {
        SomeErrorOccurredSignUp("Error Unexpected");

      }
    }
  }

  // SEND REQUEST
  request.send();

}


//#endregion AUXILIAR FUNCTIONS
