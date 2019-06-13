function up() {
    var u = $("#u");
    var url = u.attr("abbr");
    $.ajax({
        "url": url,
        "type": "GET",
        "success": function () {
            Materialize.toast('点赞成功', 2000)
        },
        error: function () {
            Materialize.toast('操作失败', 2000)
        }
    });
}

function diss() {
    var d = $("#d");
    var url = d.attr("abbr");
    $.ajax({
        "url": url,
        "type": "GET",
        "success": function () {
            Materialize.toast('踩了一脚', 2000)
        },
        error: function () {
            Materialize.toast('操作失败', 2000)
        }
    });
}