$(window).scrollTop(document.cookie);
$( document ).ready(function() {
    var popupActivator = document.getElementById("popoverData").dataset.name;
    var myPopover = document.getElementById("myPopover");
    var cards =  document.getElementsByClassName('group list-group-image');

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

    if (popupActivator === 'active') {
        $("#myPopover").popover({
            placement: 'left'
        });

        moveCardToCart(cards);

        setTimeout(function () {
            $("#myPopover").popover('show');
        }, 1500);


        setTimeout(function () {
            $(".popover").fadeOut('slow');
        }, 5000);
        document.getElementById("popoverData").dataset.name = 'passive';
    }

    var addButtons = document.getElementsByClassName('btn btn-success addButton');

    for (var i = 0; i < addButtons.length; i++) {
        addButtons[i].addEventListener('click', function () {
            document.cookie=$(window).scrollTop();
            
        })
    }
    

});


function moveCardToCart(elements) {
    var cart = $('#myPopover');

    var elementToMove = $(getElementToMove(elements));
    if (elementToMove) {
        var imgclone = elementToMove.clone()
            .offset({
                top: elementToMove.offset().top,
                left: elementToMove.offset().left
            })
            .css({
                'opacity': '1',
                'position': 'absolute',
                'width': elementToMove.width(),
                'height': elementToMove.height(),
                'z-index': '100'
            })
            .appendTo($('body'))
            .animate({
                'top': cart.offset().top + 10,
                'left': cart.offset().left + 10,
                'width': $('#myPopover').width(),
                'height': $('#myPopover').height()
            }, 1000, 'easeInOutExpo');

        setTimeout(function () {
            cart.effect("shake", {
                times: 2
            }, 100);
        }, 1500);

        imgclone.animate({
            'width': 0,
            'height': 0
        }, function () {
            $(this).detach()
        });
    }
}


function getElementToMove(elements) {
    console.log(document.getElementById('popupContentId').dataset.id);
    var elementId =  parseInt(document.getElementById('popupContentId').dataset.id);
    for (var i = 0; i < elements.length; i++) {
        if (elementId - 1  === i) {
            return elements[i];
        }
    }
}




