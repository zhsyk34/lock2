"use strict";
$(function () {
    var $grid = $("#data"), $form = $("#form"), $editor = $("#editor"), $word = $("#word");
    var $tr = $form.find("tr.number"), $gateway = $("#gatewayId"), $uuid = $("#uuid"), $name = $("#name");
        //buttons
        var $sure = $("#sure"), $enter = $("#enter"), $begin = $("#begin"), $bind = $("#bind"), $cancel = $("#cancel");

        load();
        editor();
        find();
        selector();

        function selector() {
            $gateway.combobox({
                url: "gateway/list"
            });
        }

        function find() {
            $("#search").on("click", function () {
                var params = {
                    name: $.trim($("#search-name").val())
                };
                $grid.datagrid("reload", params);
            });
            $("#reset").on("click", function () {
                $.crud.clear("#nav");
            });
        }

        function load() {
            $grid.datagrid({
                title: "门锁列表",
                url: "lock/find",
                columns: [[
                    {field: "", checkbox: true},
                    {field: "name", title: "名称", width: 10},
                    {field: "number", title: "设备号", width: 10},
                    {field: "uuid", title: "序列号", width: 10},
                    {field: "createTime", title: "创建时间", width: 10}
                ]],
                toolbar: [{
                    text: "入网",
                    iconCls: "icon-add",
                    handler: function () {
                        $form.form("clear");

                        //switching
                        state("enter");
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

                        //switching
                        state("modify");

                        $editor.dialog({title: $.message.editor}).dialog("open");
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
                            url: "lock/delete",
                            ids: ids,
                            callback: function () {
                                load();
                            }
                        });
                    }
                }, {
                    text: "修改密码",
                    iconCls: "icon-lock",
                    handler: function () {
                        var rows = $grid.datagrid("getSelections");
                        if (rows.length !== 1) {
                            $.messager.alert({title: $.message.warn, msg: "请选择一条数据"});
                            return;
                        }
                        var row = rows[0];
                        $word.data("id", row.id);
                        $("#value").numberbox("clear");
                        $word.dialog("open");
                    }
                }, {
                    text: "控制",
                    iconCls: "icon-tip",
                    handler: function () {
                        var rows = $grid.datagrid("getSelections");
                        if (rows.length !== 1) {
                            $.messager.alert({title: $.message.warn, msg: "请选择一条数据"});
                            return;
                        }
                        var row = rows[0];
                        $("#ctrl").data("uuid", row.uuid);
                        $("#ctrl").find(":radio:first").prop("checked", true);
                        $("#ctrl").dialog("open");
                    }
                }]
            });

        }

        function state(operation) {
            switch (operation) {
                case "enter":
                    $tr.hide();
                    $gateway.combobox("readonly", false);
                    $name.textbox("readonly", false);

                    $sure.hide();
                    $enter.show();
                    $begin.hide();
                    $bind.hide();
                    $cancel.show();
                    break;
                case "modify":
                    $tr.show();
                    $gateway.combobox("readonly", true);
                    $name.textbox("readonly", false);

                    $sure.show();
                    $enter.hide();
                    $begin.hide();
                    $bind.hide();
                    $cancel.show();
                    break;
                case "begin":
                    $tr.show();
                    $gateway.combobox("readonly", true);
                    $name.textbox("readonly", true);

                    $sure.hide();
                    $enter.hide();
                    $begin.show();
                    $bind.show();
                    $cancel.show();
                    break;
            }
        }

        function editor() {
            $editor.dialog();

            $cancel.on("click", function () {
                $editor.dialog("close");
            });

            $sure.on("click", function () {
                $.crud.save({
                    form: $form,
                    url: "lock/update",
                    callback: function () {
                        $.messager.progress("close");
                        load();
                        $editor.dialog("close");
                    }
                });
            });

            $enter.on("click", function () {
                $.crud.save({
                    form: $form,
                    url: "lock/enter",
                    after: function (r) {
                        $.messager.progress("close");
                        if (r) {
                            $uuid.textbox("setValue", r);

                            //switching
                            state("begin");
                        } else {
                            $editor.dialog("close");
                            $.messager.alert({title: $.message.warn, msg: "失败了"});
                        }
                    }
                });
            });

            $begin.on("click", function () {
                $.crud.save({
                    form: $form,
                    url: "lock/begin",
                    after: function (r) {
                        $.messager.progress("close");
                        if (r === "true") {
                            $.messager.alert({title: $.message.prompt, msg: "操作成功"});
                        } else {
                            $.messager.alert({title: $.message.warn, msg: "出错了"});
                        }
                    }
                });
            });

            $bind.on("click", function () {
                $.crud.save({
                    form: $form,
                    url: "lock/bind",
                    after: function (r) {
                        console.log(r);
                        $.messager.progress("close");
                        if (r) {
                            $editor.dialog("close");
                            $.messager.alert({title: $.message.prompt, msg: "绑定成功"});
                            load();
                        } else {
                            $.messager.alert({title: $.message.warn, msg: "出错了"});
                        }
                    }
                });
            });
        }

    ctrl();
    function ctrl() {
        $("#ctrl").dialog({
            title: "控制",
            buttons: [{
                text: "确定",
                iconCls: "icon-ok",
                handler: function () {
                    var uuid = $("#ctrl").data("uuid");
                    var type = $("#ctrl").find(":radio:checked").val();
                    console.log(type);
                    $.messager.progress();
                    $.ajax({
                        url: "lock/ctrl",
                        async: true,
                        data: {uuid: uuid, type: type},
                        success: function (data) {
                            console.log(data);
                            $.messager.progress("close");
                            if (data === "true") {
                                $.messager.alert({title: $.message.prompt, msg: "操作成功"});
                            } else {
                                $.messager.alert({title: $.message.prompt, msg: "操作失败"});
                            }
                        }
                    });
                }
            }, {
                text: "取消",
                iconCls: "icon-cancel",
                handler: function () {
                    $("#ctrl").dialog("close");
                }
            }]
        });
    }

        word();
        function word() {
            var $index = $word.find("tr:eq(1)"), $type = $("#type");
            $index.hide();

            $type.on("change", function () {
                var type = $(this).val();
                if (type === "user") {
                    $index.show();
                } else {
                    $index.hide();
                }
            });

            $word.dialog({
                title: "密码设置",
                buttons: [{
                    text: "修改",
                    iconCls: "icon-ok",
                    handler: function () {
                        var type = $type.val();
                        var index;
                        var value = $("#value").numberbox("getValue");
                        switch (type) {
                            case "main":
                                index = 1;
                                break;
                            case "temp":
                                index = 99;
                                break;
                            case "user":
                                index = $("#index").numberbox("getValue");
                                break;
                        }
                        var id = $word.data("id");
                        console.log(id, index, value);
                        if (index < 0 || index > 99 || value.length < 6 || value.length > 10) {
                            $.messager.alert({title: $.message.warn, msg: "数据错误"});
                            return;
                        }

                        $.messager.progress();

                        $.ajax({
                            url: "lock/word",
                            async: true,
                            data: {id: id, value: value, index: index},
                            success: function (data) {
                                console.log(data);
                                $.messager.progress("close");
                                $word.dialog("close");
                                if (data === "true") {
                                    $.messager.alert({title: $.message.prompt, msg: "修改成功"});
                                    find();
                                } else {
                                    $.messager.alert({title: $.message.prompt, msg: "操作失败"});
                                }
                            }
                        });
                    }
                }, {
                    text: "取消",
                    iconCls: "icon-cancel",
                    handler: function () {
                        $word.dialog("close");
                    }
                }]
            });
        }
    }
);