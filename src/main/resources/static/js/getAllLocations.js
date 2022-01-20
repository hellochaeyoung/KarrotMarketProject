function filter() {

    var address = $("#location").val();

   $.ajax({
        url: "/test",
        data : {
        "address" : address
        },
        type: "POST",
   }).done(function (fragment) {

      $('#resultLocationList').replaceWith(fragment);
     });

}

function setting() {

    var location = $("select[name=locations] > option:selected").val();

    $("#location").val(location);
}
