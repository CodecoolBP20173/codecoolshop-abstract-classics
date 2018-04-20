
$( document ).ready(function() {
    var popupActivator = document.getElementById("popoverData").dataset.name;
    console.log(popupActivator);
    if (popupActivator === 'active') {
        $("#myPopover").popover({
            placement: 'auto'
        });

        $("#myPopover").popover('show');
        setTimeout(function () {
            $(".popover").fadeOut('slow');
        }, 2000);
    }

});




