/*
 * Copyright 2011-2019 Hippo B.V. (http://www.onehippo.com)
 */
Ext.ns('Hippo.Reports');

const GRID_ID = "myGrid";

PageableHttpProxy = function(conn) {
    PageableHttpProxy.superclass.constructor.call(this, conn);
}

Ext.extend(PageableHttpProxy, Ext.data.HttpProxy, {

    doRequest : function(action, rs, params, reader, cb, scope, arg) {
        console.log("doRequest: start=", params.start, " limit=", " action=",  " N saved records=",this.records === undefined ? 0 : this.records.length, this.conn.url)
        cb = cb.bind(Ext.getCmp(GRID_ID).getStore());
        const url = this.conn.api.read.url;
        if (params.start !== 0 && this.records !== undefined && this.records.length !== 0 && this.recordUrl === url) {
            console.log("Returning subset of records from store", params)
            let operation = this.buildJsonObject(this.records, params, true);
            operation["reader"] = reader
            operation["scope"] = this
            operation["request"] = {"callback": cb, arg: arg}
            this.onRead("read", operation, {
                method: "GET",
                status: 200,
                responseText: JSON.stringify(operation.records.map(function(record) {return record.data})),
                params: params,
            })
            return;
        }

        this.api = undefined;
        Ext.data.Api.prepare(this);
        this.api["read"]["method"] = "GET"
        console.log("Making HTTP request to obtain records")
        PageableHttpProxy.superclass.doRequest.call(this, action, rs, params, reader, this.onRequestCompleteCallback(cb) , scope, arg);
    },

    onRequestCompleteCallback: function(loadStoreCb) {
        let self = this;
        return function(o, params, success) {
            console.log("Returned records, success=" + success)
            if (success) {
                self.records = o.records;
                self.recordUrl = this.url;
                return loadStoreCb(self.buildJsonObject(o.records, params, success), params, success);
            }

            loadStoreCb(o, params, success);
        }
    },

    translationPriorityValue: function(priority) {
        if (priority === "HIGH") return 3;
        if (priority === "NORMAL") return 2;
        return 1;
    },

    buildJsonObject: function(records, params, success) {
        if (params["params"] !== undefined) params = params.params;
        const start = params.start === undefined ? 0 : params.start;
        const limit = params.limit === undefined ? 20 : params.limit;

        // // Apply sorting to all records
        let self = this;
        const recordLength = records.length;
        // if (params.sort === "translationPriority") {
        //     records = records.sort(function (a, b) {
        //         const aVal = self.translationPriorityValue(a.data.translationPriority);
        //         const bVal = self.translationPriorityValue(b.data.translationPriority);
        //         if (aVal > bVal) return params.direction === "ASC" ? -1 : 1;
        //         if (aVal < bVal) return params.direction === "ASC" ? 1 : -1;
        //     })
        // }

        const obj =  {records: records.slice(start, start + limit), success: success, totalRecords: recordLength}
        console.log("o=", obj, start, limit, params)
        return obj;
    }
});

Hippo.Reports.TranslationListPanel = Ext.extend(Hippo.Reports.Portlet, {

    constructor: function(config) {
        const GET_UNTRANSLATED_FILES_ENDPOINT = "/cms/translation/untranslated"
        const INITIAL_LOCALE = "fr";

        let ajaxStore = new Ext.data.JsonStore({
            url: GET_UNTRANSLATED_FILES_ENDPOINT + "?locale=" + INITIAL_LOCALE,
            storeId: "myStore",
            autoLoad: true,
            remoteSort: true,
            idProperty: "handleId",
            id: "myStore",
            method: "GET",
            proxy: new PageableHttpProxy({url: GET_UNTRANSLATED_FILES_ENDPOINT + "?locale=" + INITIAL_LOCALE, api: {}}),
            fields: ["displayName", "translatedLocales", "sentForTranslationLocales", "path", "translationStatus", {name: "translationPriority", sortType: this.translationPrioritySort}, "handleId"]
        })

        var self = this;

        this.store = ajaxStore;
        this.pageSize = 20;
        this.paging = true;
        this.pagingText = "Paging text";
        this.noDataText = "No results";
        this.autoExpandColumn = "Auto Expand";

        var combo = new Ext.form.ComboBox({
            autoSelect: true,
            editable: false,
            triggerAction: 'all',
            remoteSort: true,
            lazyRender:true,
            mode: 'local',
            value: INITIAL_LOCALE,
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: [
                    'locale',
                    'displayText'
                ],
                data: [["fr", "French"], ["de", "German"], ["nl", "Dutch"], ["it", "Italian"], ["es", "Spanish"]]
            }),
            valueField: 'locale',
            displayField: 'displayText',
            listeners: {
                select: function (combo, record, index) {
                    const locale = record["id"]
                    console.log("Selected locale " + locale);
                    grid.getStore().proxy.conn.url = GET_UNTRANSLATED_FILES_ENDPOINT + "?locale=" + locale;
                    grid.getStore().load();
                }
            }
        })

        var priorityCombo = new Ext.form.ComboBox({
            autoSelect: true,
            editable: false,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: [
                    'locale',
                    'displayText'
                ],
                data: [["HIGH", "High"], ["NORMAL", "Normal"], ["LOW", "Low"]]
            }),
            valueField: 'locale',
            displayField: 'displayText'
        })

        var grid = new Ext.grid.EditorGridPanel({
            id: GRID_ID,
            store: ajaxStore,
            colModel: new Ext.grid.ColumnModel({
                defaults: {
                    menuDisabled: true,
                    sortable: false,
                    renderer: Ext.util.Format.htmlEncode
                },
                columns: [
                    {name: "name", dataIndex: "displayName", header: "Page", sortable: true, width: 50},
                    {name: "translatedLocales", dataIndex: "translatedLocales", header: "Translated", renderer: this.renderFlags, width: 10},
                    {name: "sentForTranslationLocales", dataIndex: "sentForTranslationLocales", header: "Sent for translation", renderer: this.renderFlags, width: 10},
                    {name: "translationStatus", dataIndex: "translationStatus", header: "Status", sortable: true, width: 15},
                    {name: "translationPriority", dataIndex: "translationPriority", header: "Priority", sortable: true, sortType: this.translationPrioritySort, editor: priorityCombo, width: 15},
                ]
            }),
            loadMask: false,
            autoExpandColumn: self.autoExpandColumn,
            border: false,
            disableSelection: true,
            enableColumnMove: false,
            viewConfig: {
                forceFit: true,
                scrollOffset: 0
            },
            bbar: self.paging ? new Ext.PagingToolbar({
                pageSize: self.pageSize,
                store: self.store,
                displayInfo: true,
                displayMsg: self.pagingText,
                emptyMsg: '',
                afterPageText: '',
                listeners: {
                    afterrender: function(bbar) {
                        bbar.last.hideParent = true;
                        bbar.last.hide();
                        bbar.refresh.hideParent = true;
                        bbar.refresh.hide();
                    }
                }
            }) : null,

            listeners: {
                cellclick: function(grid, rowIndex, columnIndex, event) {
                    // Only redirect user to page if title is clicked
                    if (columnIndex !== 0) return;
                    const record = grid.getStore().getAt(rowIndex);
                    self.fireEvent('documentSelected', record.data.handleId);
                },

                afteredit: function(e) {
                    if (e.originalValue === e.value) return;
                    console.log("afterEdit", e.record.id, e.record.json.handleId, e.originalValue, e.value, e)

                    self.updatePriority(e.record.id, e.value)
                        .done(function() {
                            console.log("Success!");
                        })
                        .fail(function() {
                            console.error("Failure to update priority. Restoring previous priority to " + e.originalValue);
                            // Restore previous value
                            e.record.reject()
                        })
                }
            }
        });

        grid.add(combo)
        config = Ext.apply(config, {
            // bodyCssClass: 'hippo-reports-document-list',
            items:[grid]
        });
        Hippo.Reports.TranslationListPanel.superclass.constructor.call(this, config);
    },

    renderFlags: function(flags) {
        return flags.map(function(locale) {
            locale = locale === "en" ? "uk" : locale;
            const url = "./wicket/resource/org.hippoecm.frontend.translation.LocaleProviderPlugin/icons/flags/flag-16_" + locale + ".png"
            return '<img src="' + url + '" style="display: inline" />'
        }).join(" ");
    },

    updatePriority: function(handleId, priority) {
        return $.ajax({
            url: "/cms/translation/" + handleId + "/priority",
            type: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({priority: priority})
        });
    },

    translationPrioritySort: function(value) {
        if (value === "HIGH") return 3;
        if (value === "NORMAL") return 2;
        return 1;
    },

    loadStore: function() {
        this.store.load({
            params: {
                start: 0,
                limit: this.pageSize
            }
        });
    },

    checkNoData: function(component) {
        if (this.store.getTotalCount() === 0) {
            this.showMessage(this.noDataText);
        }
    },

    initComponent: function() {
        Hippo.Reports.TranslationListPanel.superclass.initComponent.call(this);
        this.store.on('load', this.checkNoData, this);
        Hippo.Reports.RefreshObservableInstance.addListener("refresh", this.loadStore, this);
        this.loadStore();
    },

    destroy: function() {
        Hippo.Reports.TranslationListPanel.superclass.destroy.call(this);
        Hippo.Reports.RefreshObservableInstance.removeListener("refresh", this.loadStore, this);
    }

});

Ext.reg('Hippo.Reports.TranslationListPanel', Hippo.Reports.TranslationListPanel);
