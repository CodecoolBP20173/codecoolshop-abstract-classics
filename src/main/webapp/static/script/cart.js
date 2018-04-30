$(window).scrollTop(document.cookie);
$( document ).ready(function() {
    var addRemoveButtons = document.getElementsByName('addRemove');

    for (var i = 0; i < addRemoveButtons.length; i++) {
        addRemoveButtons[i].addEventListener('click', function () {
            document.cookie = $(window).scrollTop();

        })
    }

});