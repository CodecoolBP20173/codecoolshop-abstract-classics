$(window).scrollTop(document.cookie);
$( document ).ready(function() {
    var popupActivator = document.getElementById("popoverData").dataset.name;
    var myPopover = document.getElementById("myPopover");

    myPopover.addEventListener('mouseenter', function () {
        console.log('not entering');
        $(this).popover({
            placement: 'left'
        });
        $(this).attr('data-content', $('#popper-content').html());
        $(this).attr('data-original-title', 'Items in your cart:');



        console.log(document.getElementById('myPopover'));

        $("#myPopover").popover('show');
        }

    );
    myPopover.addEventListener('mouseleave', function () {
        $("#myPopover").popover('hide');
        console.log('hide')
    });

    console.log(popupActivator);
    if (popupActivator === 'active') {
        $("#myPopover").popover({
            placement: 'left'
        });

        $("#myPopover").popover('show');
        setTimeout(function () {
            $(".popover").fadeOut('slow');
        }, 2000);
        document.getElementById("popoverData").dataset.name = 'passive';
    }

    var addButtons = document.getElementsByClassName('btn btn-success addButton');

    for (var i = 0; i < addButtons.length; i++) {
        addButtons[i].addEventListener('click', function () {
            document.cookie=$(window).scrollTop();
            
        })
    }
    

});




