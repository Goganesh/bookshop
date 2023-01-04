function generatePath(type) {
    let url = '/api/v1/' + type;
    return url;
}

function deleteEntity(id, type){
    let path = generatePath(type) + "/" + id;

    $.ajax({
        url: path,
        type: "DELETE",
        contentType: 'application/json'
    })
}

function fillGenre(){
    let url = window.location.pathname;
    let pathArray = url.split("/");
    let id = pathArray[3];

    $.get(generatePath("genres/" + id)).done(function (data) {
        $('#id-input').attr('value', data.id);
        $('#parent-id-input').val(data.parentId);
        $('#slug-input').val(data.slug);
        $('#name-input').val(data.name);
    })
}

function saveGenre(){
    let obj = new Object();
    obj.id = $("#id-input").attr("value");
    obj.parentId = $("#parent-id-input").val();
    obj.slug = $("#slug-input").val();
    obj.name = $("#name-input").val();

    if(obj.name == '' || obj.parentId == '' || obj.slug == ''){
        alert('Не заполнены обязательные атрибуты');
        return;
    }

    let json = JSON.stringify(obj);
    $.ajax({
        url: "http://localhost:8085/api/v1/genres",
        type: "POST",
        data: json,
        contentType: 'application/json',

        success: function(data, status, xhr){
            window.location.replace("/admin/genres/" + data.id);
        }
    })
}