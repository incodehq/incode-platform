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

    $('.pdf-js-print').click(function () {
        raiseEvent($(this), WicketStuff.PDFJS.Topic.PRINT);
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

        var currentPage = $('.pdf-js-page-current[data-canvas-id="'+data.canvasId+'"]');
        var size = total.toString().length;

        currentPage.attr('maxLength', size);
        currentPage.css('width', size + 'em');
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

    function addZoom(options, currentZoom) {

        var newOptions = [];
        var added = false;

        for (var i = 0; i < options.length; i++) {
            var option = options[i];

            if (!added && parseFloat(currentZoom) < parseFloat(option)) {
                newOptions.push(currentZoom);
                added = true;
            }
            else if (currentZoom === option) {
                added = true;
            }

            newOptions.push(option);
        }

        if (!added){
            newOptions.push(currentZoom);
        }

        return newOptions;
    }


    function addNumericOptions(zoomDropDown, currentZoom) {
        var zoomNumericOptions = ["0.50", "0.75", "1.00", "1.25", "1.50", "2.00", "3.00", "4.00"];
        var numericOptions = addZoom(zoomNumericOptions, currentZoom);

        for (var i = 0; i < numericOptions.length; i++) {
            var zoom = numericOptions[i];

            var title = Math.floor((zoom * 100)) + "%";
            var op = "<option value='" + zoom + "' class='pdf-js-numeric'>" + title + "</option>"
            zoomDropDown.append($(op));
        }

        zoomDropDown.val(currentZoom);
    }

    Wicket.Event.subscribe(WicketStuff.PDFJS.Topic.CURRENT_ZOOM, function (jqEvent, zoom, data) {

    		var zoomDropDown =  $('.pdf-js-zoom-current[data-canvas-id="'+data.canvasId+'"]');

    		zoomDropDown.val(zoom);

    		if (!zoomDropDown.val()) {
    		    $("option.pdf-js-numeric").each(function() {
                   $(this).remove();
                });

    		    addNumericOptions(zoomDropDown, zoom);
    	    }

    	    var zoomFloat = parseFloat(zoom)
    	    var minZoom = 0.25;
    	    var maxZoom = 4.0;
    	    var $zoomInBtn = $('.pdf-js-zoom-in[data-canvas-id="'+data.canvasId+'"]');
            var $zoomOutBtn = $('.pdf-js-zoom-out[data-canvas-id="'+data.canvasId+'"]');

    	    if (zoomFloat <= minZoom){
    	        // minimum zoom
    	        $zoomOutBtn.attr("disabled", "disabled");
    	        $zoomInBtn.removeAttr("disabled");
    	    } else  if (zoomFloat >= maxZoom) {
    	        // maximum zoom
    	        $zoomOutBtn.removeAttr("disabled");
                $zoomInBtn.attr("disabled", "disabled");
    	    } else {
    	        $zoomInBtn.removeAttr("disabled");
                $zoomOutBtn.removeAttr("disabled");
    	    }

    });

});
