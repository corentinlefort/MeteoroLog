$(document).ready(function () {
    $("#submitButton").on('click',function() {
        $.get(
            "scriptAPI.php",
            {
                mode: "getWeathersStation",
                idStation : $("#idStation").val(),
            },

            function(newdata) {
                $("#zoneReponse").html(newdata);
            },
            "text"
        );
    });

    $("#getStationsButton").on('click',function() {
        $.get(
            "scriptAPI.php",
            {
                mode: "getStationsList",
            },

            function(newdata) {
                $("#zoneReponse").html(newdata);
            },
            "text"
        );
    });
})