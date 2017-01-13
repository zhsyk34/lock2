"use strict";
$(function () {
    var $grid = $("#data"), $form = $("#form"), $editor = $("#editor");

    load();
    editor();
    find();

    function find() {
        $("#search").on("click", function () {
            var params = {
                name: $.trim($("#search-name").val()),
                sn: $.trim($("#search-sn").val())
            };
            $grid.datagrid("reload", params);
        });
        $("#reset").on("click", function () {
            $.crud.clear("#nav");
        });
    }

    function load() {
        $grid.datagrid({
            title: "网关列表",
            url: "gateway/find",
            queryParams: {"pageNo": 1},
            columns: [[
                {field: "", checkbox: true},
                {field: "sn", title: "序列号", width: 10},
                {field: "name", title: "名称", width: 10},
                {field: "ip", title: "内网IP", width: 10},
                {field: "remote", title: "外网IP", width: 10},
                {field: "port", title: "端口号", width: 10},
                {field: "version", title: "版本", width: 10}
            ]],
            toolbar: [{
                text: "添加",
                iconCls: "icon-add",
                handler: function () {
                    $form.form("clear");
                    $("#sn").textbox("readonly", false);
                    $editor.dialog({title: $.message.add}).dialog("open");
                }
            }, {
                text: "修改",
                iconCls: "icon-edit",
                handler: function () {
                    var rows = $grid.datagrid("getSelections");
                    if (rows.length !== 1) {
                        $.messager.alert({title: $.message.warn, msg: "请选择一条数据"});
                        return;
                    }
                    var row = rows[0];
                    $form.form("load", row);
                    $("#sn").textbox("readonly", true);
                    $editor.dialog({title: $.message.mod}).dialog("open");
                }
            }, {
                text: $.message.del,
                iconCls: "icon-remove",
                handler: function () {
                    var rows = $grid.datagrid("getSelections");
                    if (rows.length === 0) {
                        $.messager.alert({title: $.message.warn, msg: "请选择要删除的数据"});
                        return;
                    }
                    var ids = [];
                    rows.forEach(function (row) {
                        ids.push(row.id);
                    });
                    $.crud.remove({
                        url: "gateway/delete",
                        ids: ids,
                        callback: function () {
                            load();
                        }
                    });
                }
            }]
        });
    }

    function editor() {
        $editor.dialog({
            buttons: [{
                text: $.message.sure,
                iconCls: "icon-ok",
                handler: function () {
                    $.crud.save({
                        form: $form,
                        url: "gateway/save",
                        callback: function () {
                            load();
                            $editor.dialog("close");
                        }
                    });
                }
            }, {
                text: $.message.cancel,
                iconCls: "icon-cancel",
                handler: function () {
                    $form.form("clear");
                    $editor.dialog("close");
                }
            }]
        });
    }

});