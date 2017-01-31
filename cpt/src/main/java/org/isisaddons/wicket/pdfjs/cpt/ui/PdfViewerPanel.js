$(function () {

    function raiseEvent(elem, event, moreArgs) {
        var canvasId = elem.data('canvasId');
        var args = {"canvasId": canvasId}
        if (moreArgs) {
            for (a in moreArgs){
                args[a] = moreArgs[a];
            }
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

    $('.pdf-js-zoom-current').change(function () {
        var scale = $(this).val();
        raiseEvent($(this), WicketStuff.PDFJS.Topic.ZOOM_TO, {scale : scale});
    });

    $('.pdf-js-page-current').change(function () {
        var page = parseInt($(this).val());
        raiseEvent($(this), WicketStuff.PDFJS.Topic.GOTO_PAGE, {page : page});
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

    Wicket.Event.subscribe(WicketStuff.PDFJS.Topic.CURRENT_ZOOM, function (jqEvent, zoom, data) {

    		var zoomDropDown =  $('.pdf-js-zoom-current[data-canvas-id="'+data.canvasId+'"]');

    		$("option.custom").each(function() {
                $(this).remove();
            });

    		zoomDropDown.val(zoom);

    		if (!zoomDropDown.val()) {

    		    var title = Math.floor((zoom * 100)) + "%";
    		    var op = "<option selected='selected'  value='" + zoom + "' class='custom'>" + title + "</option>"
    		    zoomDropDown.append($(op));
    	    }
    });

});
