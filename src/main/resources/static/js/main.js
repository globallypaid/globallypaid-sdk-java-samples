const gpg = new GloballyPaidSDK('pk_test_7NRTHP40PVVK45T06NEA5JKGKFWE9Q3Y');

$(document).ready(function () {
    const chargeSimple = gpg.createForm('card-simple', {
                                 style: {
                                     base: {
                                         width: '560px',
                                         buttonColor: 'white',
                                         buttonBackground: '#558b2f',
                                         inputColor: '#558b2f',
                                         color: '#558b2f'
                                     }
                                 }
                             });

    chargeSimple.mount('example1');

    chargeSimple.on('TOKEN_CREATION',
        (token) => {
            console.log(token);
            // Do whatever you need with the token.
            // Send a request to yours backend to perform a charge request
            make_charge(token, chargeSimple);
        },
        (error) => {
            chargeSimple.showError();
            console.log(error);
        }
    );
});

function make_charge(token, chargeSimple) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/charge",
        data: JSON.stringify(token),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var json = "<h4>Charge Response</h4><pre class=\"alert alert-success\" role=\"alert\">"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            chargeSimple.showSuccess();
            console.log("SUCCESS : ", data);
        },
        error: function (e) {
            var json = "<h4>Charge Response</h4><pre class=\"alert alert-danger\" role=\"alert\">"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
            chargeSimple.showError();
            console.log("ERROR : ", e);
        }
    });

}