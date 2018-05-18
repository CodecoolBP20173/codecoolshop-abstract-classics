$(window).scrollTop(getCookie("cartScrollPosition"));
$( document ).ready(function() {
    var addRemoveButtons = document.getElementsByName('addRemove');

    for (var i = 0; i < addRemoveButtons.length; i++) {
        addRemoveButtons[i].addEventListener('click', function () {
            document.cookie="cartScrollPosition=" + $(window).scrollTop();

        })
    }


});

function getCookie(name) {
    let value = "; " + document.cookie;
    let parts = value.split("; " + name + "=");
    if (parts.length === 2) return parts.pop().split(";").shift();
}