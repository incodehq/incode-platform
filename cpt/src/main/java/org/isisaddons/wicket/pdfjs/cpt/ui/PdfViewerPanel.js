$(function () {

    function raiseEvent(elem, event, page) {
        var canvasId = elem.data('canvasId');
        var args = {"canvasId": canvasId}
        if (page) {
            args["page"] = parseInt(page);
        }
        Wicket.Event.publish(event, args);
    }

    $('.pdf-js-page-prev').click(function () {
        raiseEvent($(this), WicketStuff.PDFJS.Topic.PREVIOUS_PAGE);
    });

    $('.pdf-js-page-next').click(function () {
        raiseEvent($(this), WicketStuff.PDFJS.Topic.NEXT_PAGE);
    });

    $('.pdf-js-zoom-in').click(function () {
        raiseEvent($(this), WicketStuff.PDFJS.Topic.ZOOM_IN);
    });

    $('.pdf-js-zoom-out').click(function () {
        raiseEvent($(this), WicketStuff.PDFJS.Topic.ZOOM_OUT);
    });


    $('.pdf-js-page-current').change(function () {
        var page = $(this).val();
        raiseEvent($(this), WicketStuff.PDFJS.Topic.GOTO_PAGE, page);
    });

    Wicket.Event.subscribe(WicketStuff.PDFJS.Topic.CURRENT_PAGE, function (jqEvent, pageNumber, data) {
        $('.pdf-js-page-current[data-canvas-id="'+data.canvasId+'"]').val(pageNumber);

        var $prevPageBtn = $('.pdf-js-page-prev[data-canvas-id="'+data.canvasId+'"]');
        if (pageNumber === 1) {
            $prevPageBtn.attr("disabled", "disabled");
        } else {
            $prevPageBtn.removeAttr("disabled");
        }

        var $nextPageBtn = $('.pdf-js-page-next[data-canvas-id="'+data.canvasId+'"]');
        if (pageNumber === $nextPageBtn.data("total-pages")) {
            $nextPageBtn.attr("disabled", "disabled");
        } else {
            $nextPageBtn.removeAttr("disabled");
        }
    });

    Wicket.Event.subscribe(WicketStuff.PDFJS.Topic.TOTAL_PAGES, function (jqEvent, total, data) {
        $('.pdf-js-page-total[data-canvas-id="'+data.canvasId+'"]').text(total);

        var $nextPageBtn = $('.pdf-js-page-next[data-canvas-id="'+data.canvasId+'"]');
        $nextPageBtn.data("total-pages", total);
        if (total > 1) {
            $nextPageBtn.removeAttr("disabled");
        }
    });
});
