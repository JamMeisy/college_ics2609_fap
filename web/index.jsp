<%-- Document : index Created on : 05 1, 24, 3:05:50 PM Author : Jam --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ActiveLearning PH</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/styles-index.css"/>
        <link
                rel="stylesheet"
                href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400&display=swap"/>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://kit.fontawesome.com/f5ce89fef9.js" crossorigin="anonymous"></script>
        <script>
            $(document).ready(function () {
                $("#captcha").on("paste", function (e) {
                    e.preventDefault();
                });

                $("#generatedCaptcha").on("copy", function (e) {
                    e.preventDefault();
                });
            });

            function refreshCaptcha() {
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        document.getElementById("generatedCaptcha").value =
                            xhr.responseText;
                    }
                };
                xhr.open("GET", "captcha", true);
                xhr.send();
            }

            function onPageShow() {
                refreshCaptcha();
            }

            window.addEventListener("pageshow", onPageShow);
        </script>
    </head>
    <body>

        <!-- Body  -->
        <div class="login-box">
            <div class="label-container">
                <h2>Welcome to <span class="bold">Active Learning!</span></h2>
                <h4>Login to Activate Learning to start Your IT career!</h4>
            </div>
            <form action="login" method="POST">
                <input type="text" name="username" id="username" placeholder="  Email address" required/>

                <input type="password" name="password" id="password" placeholder="  Password" required/>

                <label for="generatedCaptcha">Generated CAPTCHA:</label>
                <input
                        type="text"
                        name="generatedCaptcha"
                        id="generatedCaptcha"
                        readonly
                        onfocus="this.blur()"
                />

                <input
                        type="text"
                        name="captcha"
                        id="captcha"
                        onpaste="return false;"
                        placeholder="  Type the Characters above" 
                        required
                />

                <div class="button-container">
                    <button
                            type="button"
                            onclick="refreshCaptcha()"
                            class="refresh-button"
                    >
                        &#8635;
                    </button>
                    <button type="submit" class="submit-button">Submit</button>
                </div>
            </form>
        </div>
        <img class="img-photo" src="assets/UST.jpg" alt="UST">
    </body>
</html>
