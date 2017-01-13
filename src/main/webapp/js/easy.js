(function ($) {
    $.extend($.fn.dialog.defaults, {
        top: 100,
        closed: true,
        cache: false,
        modal: true,
        closable: false
    });

    $.extend($.messager.defaults, {
        top: 100,
        zIndex: 9999
    });

    $.extend($.fn.accordion.defaults, {
        multiple: true
    });

    $.extend($.fn.datagrid.defaults, {
        fit: true,
        fitColumns: true,
        striped: true,
        pagination: true,
        rownumbers: true,
    });

    $.extend($.fn.combobox.defaults, {
        editable: false,
        mode: "remote",
        method: "POST",
        valueField: "id",
        textField: "name",
        required: true,
        panelWidth: 180
    });
})(jQuery);
