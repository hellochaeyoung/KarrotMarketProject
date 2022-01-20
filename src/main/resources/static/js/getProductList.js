function getProductList() {

    var status = $("#status").val();
    var member = ${"#memberId"}.val();
    var nickName = ${"#nickName"}.val()

    console.log(member);

    var param = {"status":status, "memberId":member, "nickName":nickName}

   $.ajax({
        url: "/products/all",
        contentType: "application/json",
        data: JSON.stringify(param),
        type: "POST",
   }).done(function (fragment) {
    $('#resultTable').replaceWith(fragment);
   });

}