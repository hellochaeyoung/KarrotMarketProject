function clickOption() {

    var category = $("select[name=category] > option:selected").val();

    var param = {"category": category}

   $.ajax({
        url: "/",
        contentType: "application/json",
        data: JSON.stringify(param),
        type: "POST",
   }).done(function (fragment) {
        $('#resultTable').replaceWith(fragment);
   });

}