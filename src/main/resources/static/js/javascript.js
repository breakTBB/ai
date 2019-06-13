var is_url=false;
function searchByUrl(){
	var url=document.getElementById("searchViaUrl").value.replace(/(^\s*)|(\s*$)/g, '');
	var but=document.getElementById("search_b");
	if(url!==undefined&&url!==''&&url!=null){
		is_url=true;
		but.setAttribute("style","border-color: #0073eb;background-color: #0073eb;color: #fff;");
	}
	else{
		is_url=false;
		but.setAttribute("style","border-color: #606060;background-color: rgba(0,0,0,.45);color: black;");
	}
}

function detectUrl(){
	if(is_url){
		var picUrl = document.getElementById("searchViaUrl").value;
		var id_user = $("input[name='id_user']").val();
		var category = $("input[name='category']:checked").val();

		var formData = new FormData();
		formData.append("picUrl", picUrl);
		formData.append("category", category);
		formData.append("id_user", id_user);

		if (category === undefined) {
			alert("请选择类别！");
			return;
		}
		document.getElementById("background").style.backgroundImage="url("+document.getElementById("searchViaUrl").value+")";
		$.ajax({
			"url": "detect_url",
			"type": "POST",
			"data": formData,
			contentType: false,
			processData: false,
			"success": function (data) {
				alert(data);
			},
			error: function () {
				alert("error");
			}
		});
	}
}

function searchByLocal(){
	var file = $("#search_local").prop("files")[0];
	var id_user = $("input[name='id_user']").val();
	var category = $("input[name='category']:checked").val();
	var formData = new FormData();
	formData.append("file", file);
	formData.append("category", category);
	formData.append("id_user", id_user);

	if (category === undefined) {
		alert("请选择类别！");
		return;
	}
	document.getElementById("background").style.backgroundImage="url("+window.URL.createObjectURL(document.getElementById("search_local").files.item(0))+")";

	$.ajax({
		"url": "/picture",
		"type": "POST",
		"data": formData,
		contentType: false,
		processData: false,
		"success": function (data) {
			alert(data);
		},
		error: function () {
			alert("error");
		}
	});
}