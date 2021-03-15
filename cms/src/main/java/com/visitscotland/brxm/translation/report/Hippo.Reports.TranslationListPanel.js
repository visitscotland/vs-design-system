/*
 * Copyright 2011-2019 Hippo B.V. (http://www.onehippo.com)
 */
Ext.ns('Hippo.Reports');

const GRID_ID = "myGrid";
const PAGE_SIZE = 13

Hippo.Reports.TranslationListPanel = Ext.extend(Hippo.Reports.Portlet, {

    constructor: function(config) {
        const GET_UNTRANSLATED_FILES_ENDPOINT = "/cms/translation/untranslated"
        const INITIAL_LOCALE = "fr";

        let ajaxStore = new Ext.data.JsonStore({
            storeId: "myStore",
            autoLoad: true,
            remoteSort: true,
            idProperty: "handleId",
            id: "myStore",
            root: "data",
            totalProperty: "total",
            method: "GET",
            proxy: new Hippo.Reports.PageableHttpProxy({url: GET_UNTRANSLATED_FILES_ENDPOINT, api: {}}, {locale: INITIAL_LOCALE}),
            fields: ["displayName", "translatedLocales", "sentForTranslationLocales", "path", "translationStatus",
                "translationPriority", "handleId", "lastModified",
                "publishStatus", "type"]
        })

        var self = this;

        this.store = ajaxStore;
        this.pageSize = PAGE_SIZE;
        this.paging = true;
        this.noDataText = "No results";


        const languageComboConfig = {
            xtype: "combo",
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
                    const gridStore = Ext.getCmp(GRID_ID).getStore()
                    gridStore.proxy.setParams({locale: locale});
                    gridStore.load();
                }
            }
        }

        const publishStatusComboConfig = {
            xtype: 'combo',
            autoSelect: true,
            editable: false,
            triggerAction: 'all',
            lazyRender: true,
            mode: 'local',
            value: "all",
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: [
                    'code',
                    'displayText'
                ],
                data: [["all", "All documents"], ["published", "Published"], ["offline", "Offline"]]
            }),
            valueField: 'code',
            displayField: 'displayText',
            listeners: {
                select: function (combo, record, index) {
                    const selected = record["id"]
                    const proxy = Ext.getCmp(GRID_ID).getStore().proxy;
                    console.log("publish selected" , selected)
                    if (selected === "published") {
                        proxy.addFilter("publishStatus", (record, value) => {
                            return value === "CURR_VERSION_LIVE" || value === "PREV_VERSION_LIVE"
                        })
                    } else if (selected === "offline") {
                        proxy.addFilter("publishStatus", (record, value) => {
                            return value === "NOT_LIVE";
                        })
                    } else {
                        proxy.clearFilter("publishStatus")
                    }
                    Ext.getCmp(GRID_ID).getStore().load();
                }
            }
        };

        // Combo box used to edit each record's priority
        // Configured in the editor grid as a column editor component
        // Requests for editing made in the editor grid edit event callback
        const priorityComboConfig = {
            xtype: "combo",
            autoSelect: true,
            editable: false,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'remote',
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: [
                    'value',
                    'name',
                    'sortOrder'
                ],
                proxy: new Ext.data.HttpProxy({
                    // Get possible priority values from the server. Configured in com.visitscotland.brxm.translation.report.TranslationPriority
                    url: "/cms/translation/priority",
                    method: "GET"
                })
            }),
            valueField: 'value',
            displayField: 'name'
        }

        // Combo box used for filtering records, shown in the left hand side
        const priorityFilterComboConfig = {
            xtype: "combo",
            autoSelect: true,
            editable: false,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            value: "All documents",
            store: new Ext.data.ArrayStore({
                id: 0,
                autoLoad: true,
                fields: [
                    'value',
                    'name',
                    'sortOrder'
                ],
                proxy: new Ext.data.HttpProxy({
                    // Get possible priority values from the server. Configured in com.visitscotland.brxm.translation.report.TranslationPriority
                    url: "/cms/translation/priority",
                    method: "GET"
                }),

                listeners: {
                    load: (store) => {
                        store.insert(0, new Ext.data.Record({
                            value: "all",
                            name: "All documents",
                            sortOrder: 0
                        }, "all"))
                    }
                }
            }),

            valueField: 'value',
            displayField: 'name',
            listeners: {
                select: (combo, record, index) => {
                    console.log("Filter translationPriority", record);
                    const proxy = Ext.getCmp(GRID_ID).getStore().proxy;
                    if (record === "all") {
                        proxy.clearFilter("translationPriority");
                    } else {
                        proxy.addFilter("translationPriority", (rec, value) => value === record["id"])
                    }

                    Ext.getCmp(GRID_ID).getStore().load();
                }
            }

        }


        const editorGridPanelConfig = {
            xtype: "editorgrid",
            id: GRID_ID,
            store: ajaxStore,
            stateful: false,
            colModel: new Ext.grid.ColumnModel({
                defaults: {
                    menuDisabled: true,
                    sortable: false,
                    renderer: Ext.util.Format.htmlEncode
                },
                columns: [
                    {name: "publishStatus", dataIndex: "publishStatus", header: "", sortable: true, renderer: this.renderPublishStatus, width: 5},
                    {name: "name", dataIndex: "displayName", header: "Page", sortable: true, width: 30},
                    {name: "type", dataIndex: "type", header: "Type", sortable: true, width: 10},
                    {name: "lastModified", dataIndex: "lastModified", header: "Last modified", sortable: true, renderer: this.renderDateTime, width: 5},
                    {name: "translatedLocales", dataIndex: "translatedLocales", header: "Translated", renderer: this.renderFlags, width: 10},
                    {name: "sentForTranslationLocales", dataIndex: "sentForTranslationLocales", header: "Sent for translation", renderer: this.renderFlags, width: 10},
                    {name: "translationStatus", dataIndex: "translationStatus", header: "Status", sortable: true, width: 15},
                    {name: "translationPriority", dataIndex: "translationPriority", header: "Priority", sortable: true, editor: priorityComboConfig, renderer: {fn: this.renderTranslationPriority, scope: self}, width: 15},
                ]
            }),
            loadMask: false,
            border: false,
            disableSelection: true,
            enableColumnMove: false,
            viewConfig: {
                forceFit: true,
                scrollOffset: 0,
                markDirty: false
            },
            bbar: self.paging ? new Ext.PagingToolbar({
                pageSize: self.pageSize,
                store: self.store,
                displayInfo: true,
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
                cellclick: (grid, rowIndex, columnIndex, event) => {
                    // Only redirect user to page if title is clicked
                    if (columnIndex !== 0) return;
                    const record = grid.getStore().getAt(rowIndex);
                    self.fireEvent('documentSelected', record.data.handleId);
                },

                afteredit: (e) =>  {
                    if (e.originalValue === e.value) return;
                    if (e.field !== "translationPriority") return;
                    console.log("afterEdit", e.record.id, e.record.json.handleId, e.originalValue, e.value, e)

                    self.updatePriority(e.record.json.handleId, e.value)
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
        }

        config = Ext.apply(config, {
            // bodyCssClass: 'hippo-reports-document-list',
            layout: "border",
            items: [
                {
                    region: "center",
                    layout: "fit",
                    items: [editorGridPanelConfig]
                },
                {
                    region: 'west',
                    width: 200,
                    items: [
                        {
                            xtype: "label",
                            text: "Language",
                        },
                        languageComboConfig,
                        {
                            xtype: "label",
                            text: "Publish status"
                        },
                        publishStatusComboConfig,
                        {
                            xtype: "label",
                            text: "Priority"
                        },
                        priorityFilterComboConfig
                    ]
                }
            ]
        });

        Hippo.Reports.TranslationListPanel.superclass.constructor.call(this, config);
    },

    getPriorityData: function() {
        return new Promise((resolve, reject) => {
            fetch("/cms/translation/priority")
                .then((response) => {
                    response.json().then((priorityData) => {
                        resolve(priorityData)
                    })
                })
                .catch((err) => reject(err))
        })
    },

    renderFlags: function(flags) {
        return flags.map(function(locale) {
            locale = locale === "en" ? "uk" : locale;
            const url = "./wicket/resource/org.hippoecm.frontend.translation.LocaleProviderPlugin/icons/flags/flag-16_" + locale + ".png"
            return '<img src="' + url + '" style="display: inline" />'
        }).join(" ");
    },

    renderDateTime: function(iso) {
        const date = new Date(iso);
        const display = date.dateFormat("d M Y")
        const onHover = date.toLocaleString()
        return '<p title="' + onHover + '">' + display + '</p>'
    },

    renderPublishStatus: function(status) {
        let topIcon = "hi-empty"
        let bottomIcon = "hi-empty"
        if (status === "NOT_LIVE") {
            topIcon = "hi-minus-circle"
        } else if (status === "PREV_VERSION_LIVE") {
            topIcon = "hi-check-circle";
            bottomIcon = "hi-exclamation-triangle"
        } else if (status === "CURR_VERSION_LIVE") {
            topIcon = "hi-check-circle";
            bottomIcon = "hi-empty";
        }

        return "<div class=\"hi hi-stack hi-l\">" +
            " <svg class=\"hi hi-file-text hi-l\"><use xlink:href=\"#hi-file-text-l\"></use></svg>" +
            " <svg class=\"hi " + topIcon + " hi-m hi-left hi-top\"><use xlink:href=\"#"+ topIcon +"-m\"></use></svg>" +
            " <svg class=\"hi " + bottomIcon +  " hi-m hi-left hi-bottom\"><use xlink:href=\"#" + bottomIcon + "-m\"></use></svg></div>"
    },

    renderTranslationPriority: function(priority) {
        // this = TranslationListPanel
        if (this.priorityDropdownData === undefined) {
            this.priorityDropdownData = []
            this.getPriorityData()
                .then((priorityData) => {
                    this.priorityDropdownData = priorityData;
                    const gridStore = Ext.getCmp(GRID_ID).getStore()
                    gridStore.load()
                    var self = this;
                    gridStore.proxy.addSortTransformation("translationPriority", (a) => {
                        for (let i = 0; i < self.priorityDropdownData.length; i++) {
                            if (self.priorityDropdownData[i][0] === a) return self.priorityDropdownData[i][2];
                        }
                        return 0;
                    })
                })
            return "";
        } else if (this.priorityDropdownData.length === 0) {
            return ""
        } else {
            for (let i = 0; i < this.priorityDropdownData.length; i++) {
                if (this.priorityDropdownData[i][0] === priority) return this.priorityDropdownData[i][1];
            }
            return "error";
        }
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


    loadStore: function() {
        this.store.load({
            params: {
                start: 0,
                limit: this.pageSize
            }
        });
    },

    initComponent: function() {
        Hippo.Reports.TranslationListPanel.superclass.initComponent.call(this);
        Hippo.Reports.RefreshObservableInstance.addListener("refresh", this.loadStore, this);
        this.loadStore();
    },

    destroy: function() {
        Hippo.Reports.TranslationListPanel.superclass.destroy.call(this);
        Hippo.Reports.RefreshObservableInstance.removeListener("refresh", this.loadStore, this);
    }

});

Ext.reg('Hippo.Reports.TranslationListPanel', Hippo.Reports.TranslationListPanel);
