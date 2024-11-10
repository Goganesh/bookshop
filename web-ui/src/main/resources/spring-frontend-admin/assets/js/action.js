function generatePath(type) {
    let url = '/api/v1/' + type;
    return url;
}

function deleteEntity(id, type){
    let path = generatePath(type) + "/" + id;

    $.ajax({
        url: path,
        type: "DELETE",
        contentType: 'application/json',

        success: function(data, status, xhr){
            window.location.replace("/admin/" + type);
        }
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

function fillAuthor(){
    let url = window.location.pathname;
    let pathArray = url.split("/");
    let id = pathArray[3];

    $.get(generatePath("authors/" + id)).done(function (data) {
        $('#id-input').attr('value', data.id);
        $('#slug-input').val(data.slug);
        $('#name-input').val(data.name);
        $('#description-input').val(data.description);
    })
}

function fillBook(){
    let url = window.location.pathname;
    let pathArray = url.split("/");
    let id = pathArray[3];

    $.get(generatePath("books/" + id)).done(function (data) {
        $('#id-input').attr('value', data.id);
        $('#slug-input').val(data.slug);
        $('#title-input').val(data.title);
        $('#description-input').val(data.description);
        $('#pub-date-input').val(data.pubDate);
        $('#price-input').val(data.price);
        $('#discount-input').val(data.discount);
    })
}

function fillUser(){
    let url = window.location.pathname;
    let pathArray = url.split("/");
    let id = pathArray[3];

    $.get(generatePath("users/" + id)).done(function (data) {
        $('#id-input').attr('value', data.id);
        $('#hash-input').val(data.hash);
        $('#name-input').val(data.name);
        $('#balance-input').val(data.balance);
        $('#role-input').val(data.role);
    })
}

function fillBookImage(){
    let url = window.location.pathname;
    let pathArray = url.split("/");
    let id = pathArray[3];

    $.get(generatePath("books/" + id)).done(function (data) {
        $('#book-image').attr('src', data.image);
    })
}

function fillReview(){
    let url = window.location.pathname;
    let pathArray = url.split("/");
    let id = pathArray[3];

    $.get(generatePath("reviews/" + id)).done(function (data) {
        $('#id-input').attr('value', data.id);
        $('#text-input').val(data.text);
    })
}

function fillBookImage(){
    let url = window.location.pathname;
    let pathArray = url.split("/");
    let id = pathArray[3];

    $.get(generatePath("books/" + id)).done(function (data) {
        $('#book-image').attr('src', data.image);
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
            window.location.replace("/admin/genres");
        }
    })
}

function saveAuthor(){
    let obj = new Object();
    obj.id = $("#id-input").attr("value");
    obj.slug = $("#slug-input").val();
    obj.name = $("#name-input").val();
    obj.description = $("#description-input").val();

    if(obj.name == '' || obj.slug == ''){
        alert('Не заполнены обязательные атрибуты');
        return;
    }

    let json = JSON.stringify(obj);
    $.ajax({
        url: "http://localhost:8085/api/v1/authors",
        type: "POST",
        data: json,
        contentType: 'application/json',

        success: function(data, status, xhr){
            window.location.replace("/admin/authors");
        }
    })
}

function saveBook(){
    let obj = new Object();
    obj.id = $("#id-input").attr("value");
    obj.slug = $("#slug-input").val();
    obj.title = $("#title-input").val();
    obj.description = $("#description-input").val();
    obj.pubDate = $("#pub-date-input").val();
    obj.price = $("#price-input").val();
    obj.discount = $("#discount-input").val();

    if(obj.title == '' || obj.slug == ''){
        alert('Не заполнены обязательные атрибуты');
        return;
    }

    let json = JSON.stringify(obj);
    $.ajax({
        url: "http://localhost:8085/api/v1/books",
        type: "POST",
        data: json,
        contentType: 'application/json',

        success: function(data, status, xhr){
            window.location.replace("/admin/books");
        }
    })
}

function saveUser(){
    let obj = new Object();
    obj.id = $("#id-input").attr("value");
    obj.hash = $("#hash-input").val();
    obj.name = $("#name-input").val();
    obj.balance = $("#balance-input").val();
    obj.role = $("#role-input").val();

    if(obj.name == '' || obj.hash == '' || obj.role == ''){
        alert('Не заполнены обязательные атрибуты');
        return;
    }

    let json = JSON.stringify(obj);
    $.ajax({
        url: "http://localhost:8085/api/v1/users",
        type: "POST",
        data: json,
        contentType: 'application/json',

        success: function(data, status, xhr){
            window.location.replace("/admin/users");
        }
    })
}

function saveReview(){
    let obj = new Object();
    obj.id = $("#id-input").attr("value");
    obj.text = $("#text-input").val();

    if(obj.text == ''){
        alert('Не заполнены обязательные атрибуты');
        return;
    }

    let json = JSON.stringify(obj);
    $.ajax({
        url: "http://localhost:8085/api/v1/reviews",
        type: "POST",
        data: json,
        contentType: 'application/json',

        success: function(data, status, xhr){
            window.location.replace("/admin/reviews");
        }
    })
}

function uploadFile() {
    let form = $('#fileUploadForm')[0];
    let data = new FormData(form);

    let url = window.location.pathname;
    let pathArray = url.split("/");
    let id = pathArray[3];

    $.ajax({
        url: "http://localhost:8085/api/v1/books/" + id + "/image",
        type: "POST",
        enctype: 'multipart/form-data',
        data: data,
        cache: false,
        processData: false,
        contentType: false,
        success: function(response) {
            window.location.replace("/admin/books");
        },
        error: function(jqXHR, textStatus, errorMessage) {
            console.log(errorMessage); // Optional
        }
    });
}