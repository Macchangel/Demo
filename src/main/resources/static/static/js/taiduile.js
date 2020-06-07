$(document).ready(function(){
    var url = "http://localhost:8080/show"
    $("#submit").click(function(){
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        var keyword = $("#keyword").val();
        $.ajax({
            type: "get",
            url: url,
            dateType: "text",
            data: {
                startDate:startDate,
                endDate:endDate,
                keyword:keyword
            },
            success: function (data) {
                alert("success: " + data)
            },
            error: function (data, status, errorThrown) {
                alert("error: " + data + status + errorThrown);
                console.log(data)
            }
        })

    });
});