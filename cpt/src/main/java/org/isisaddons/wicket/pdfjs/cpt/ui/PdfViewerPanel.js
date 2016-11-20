$(function () {

    $('.pdf-js-page-prev').click(function () {
        var canvasId = $(this).data('canvasId');
        Wicket.Event.publish(WicketStuff.PDFJS.Topic.PREVIOUS_PAGE, {"canvasId": canvasId});
    });
    $('.pdf-js-page-next').click(function () {
        var canvasId = $(this).data('canvasId');
        Wicket.Event.publish(WicketStuff.PDFJS.Topic.NEXT_PAGE, {"canvasId": canvasId});
    });

    Wicket.Event.subscribe(WicketStuff.PDFJS.Topic.CURRENT_PAGE, function (jqEvent, pageNumber, data) {
        $('.pdf-js-page-current[data-canvas-id="'+data.canvasId+'"]').text(pageNumber);

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
