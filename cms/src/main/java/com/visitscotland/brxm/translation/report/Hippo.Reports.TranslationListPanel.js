/*
 * Copyright 2011-2019 Hippo B.V. (http://www.onehippo.com)
 */
Ext.ns('Hippo.Reports');

const GRID_ID = "myGrid";
const MODULE_FILTER_ID = "moduleFilter"
const MODULE_LABEL_ID = "moduleFilterLabel"
const PAGE_FILTER_ID = "pageFilter"
const PAGE_LABEL_FILTER_ID = "pageFilterLabel"
const FILTER_SIDEBAR_ID = "filterSidebar"
const PAGE_SIZE = 13

const SIDEBAR_LABEL_STYLE = "display: block; margin-top: 20px";

// Simply loads records from a simple list, such as [1, 3, 6, 7]
// Normal ArrayReader requires each list item to be in a sublist
Hippo.Reports.SimpleArrayReader = Ext.extend(Ext.data.JsonReader,{
    readRecords: function (list) {
        return {
            success : true,
            records : list.map((item) => new this.recordType({value: item}, item)),
            totalRecords : list.length
        };
   }
});


Hippo.Reports.SimpleArrayStore = Ext.extend(Ext.data.Store, {
    constructor: function (config) {
        Hippo.Reports.SimpleArrayStore.superclass.constructor.call(this, Ext.apply(config, {
            reader: new Hippo.Reports.SimpleArrayReader(config)
        }));
    }
});


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
                "translationPriority", "handleId", "lastModified", "publishStatus", "type"]
        })

        var self = this;

        this.store = ajaxStore;
        this.pageSize = PAGE_SIZE;
        this.paging = true;
        this.pageTypes =[]
        this.moduleTypes = []


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
                    if (record["id"] === "all") {
                        proxy.clearFilter("translationPriority");
                    } else {
                        proxy.addFilter("translationPriority", (rec, value) => value === record["id"])
                    }

                    Ext.getCmp(GRID_ID).getStore().load();
                }
            }
        }

        // Combo box used for filtering between Pages and modules
        // Gets possible page and module types from api
        const pageOrModuleFilterComboConfig = {
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
                data: [["all", "All documents"], ["pages", "Pages"], ["modules", "Modules"]]
            }),
            valueField: 'code',
            displayField: 'displayText',
            listeners: {
                select: function (combo, record, index) {
                    console.log("Filter select", self.moduleTypes, self.pageTypes);
                    const store = Ext.getCmp(GRID_ID).getStore();
                    if (record["id"] === "all") {
                        store.proxy.clearFilter("type");
                        self.removePageSubtypeFilters.call(self)
                    }
                    if ( record["id"] === "pages") {
                        self.showPageFilter.call(self)
                    } else if (record["id"] === "modules") {
                        self.showModuleFilter.call(self)
                    }
                    if (self.moduleTypes.length === 0 || self.pageTypes.length === 0) return;
                    const allowedTypes = record["id"] === "pages" ? self.pageTypes : self.moduleTypes;
                    store.proxy.addFilter("type", (rec, val) => allowedTypes.includes(val));
                    store.load();
                }
            }
        };

        // Filter allows user to filter by module
        // Added dynamically when page/module filter = module
        this.moduleFilterComboConfig = {
            xtype: "combo",
            id: MODULE_FILTER_ID,
            autoSelect: true,
            editable: false,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            value: "All documents",
            store: new Hippo.Reports.SimpleArrayStore({
                id: 0,
                autoLoad: true,
                fields: [
                    'value',
                ],
                proxy: new Ext.data.HttpProxy({
                    url: "/cms/translation/modules",
                    method: "GET"
                }),

                listeners: {
                    load: (store) => {
                        store.insert(0, new Ext.data.Record({
                            value: "All documents",
                        }, "All documents"))
                    }
                }
            }),

            valueField: 'value',
            displayField: 'value',
            listeners: {
                select: (combo, record, index) => {
                    console.log("Filter module type", record);
                    const proxy = Ext.getCmp(GRID_ID).getStore().proxy;
                    if (record["id"] === "All documents") {
                        proxy.clearFilter("type");
                    } else {
                        proxy.addFilter("type", (rec, value) => value === record["id"])
                    }

                    Ext.getCmp(GRID_ID).getStore().load();
                }
            }
        }

        this.pageFilterComboConfig = {
            xtype: "combo",
            id: PAGE_FILTER_ID,
            autoSelect: true,
            editable: false,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            value: "All documents",
            store: new Hippo.Reports.SimpleArrayStore({
                id: 0,
                autoLoad: true,
                fields: [
                    'value',
                ],
                proxy: new Ext.data.HttpProxy({
                    url: "/cms/translation/pages",
                    method: "GET"
                }),

                listeners: {
                    load: (store) => {
                        store.insert(0, new Ext.data.Record({
                            value: "All documents",
                        }, "All documents"))
                    }
                }
            }),

            valueField: 'value',
            displayField: 'value',
            listeners: {
                select: (combo, record, index) => {
                    console.log("Filter page type", record);
                    const proxy = Ext.getCmp(GRID_ID).getStore().proxy;
                    if (record["id"] === "All documents") {
                        proxy.clearFilter("type");
                    } else {
                        proxy.addFilter("type", (rec, value) => value === record["id"])
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
                    {name: "name", dataIndex: "displayName", header: "Document", sortable: true, width: 30},
                    {name: "type", dataIndex: "type", header: "Type", sortable: true, width: 10},
                    {name: "lastModified", dataIndex: "lastModified", header: "Last modified", sortable: true, renderer: this.renderDateTime, width: 5},
                    {name: "translatedLocales", dataIndex: "translatedLocales", header: "Translated", renderer: this.renderFlags, width: 10},
                    {name: "sentForTranslationLocales", dataIndex: "sentForTranslationLocales", header: "Sent for translation", renderer: this.renderFlags, width: 10},
                    {name: "translationStatus", dataIndex: "translationStatus", header: "Translation status", sortable: true, width: 15},
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
                    if (columnIndex !== 1) return;
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

        this.moduleLabel = {
            xtype: "label",
            text: "Module type",
            id: MODULE_LABEL_ID,
            style: SIDEBAR_LABEL_STYLE
        }

        this.pageLabel = {
            xtype: "label",
            text: "Page type",
            id: PAGE_LABEL_FILTER_ID,
            style: SIDEBAR_LABEL_STYLE
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
                    layout: "anchor",
                    region: 'west',
                    id: FILTER_SIDEBAR_ID,
                    anchorSize: {width:200, height:600},
                    width: 200,
                    items: [
                        {
                            xtype: "label",
                            text: "Language",
                            anchor: "100%"
                        },
                        languageComboConfig,
                        {
                            xtype: "label",
                            text: "Publish status",
                            style: SIDEBAR_LABEL_STYLE
                        },
                        publishStatusComboConfig,
                        {
                            xtype: "label",
                            text: "Priority",
                            style: SIDEBAR_LABEL_STYLE
                        },
                        priorityFilterComboConfig,
                        {
                            xtype: "label",
                            text: "Document type",
                            style: SIDEBAR_LABEL_STYLE
                        },
                        pageOrModuleFilterComboConfig
                    ]

                }
            ]
        });

        Hippo.Reports.TranslationListPanel.superclass.constructor.call(this, config);
    },

    removePageSubtypeFilters() {
        const cmp = Ext.getCmp(FILTER_SIDEBAR_ID)
        cmp.remove(MODULE_FILTER_ID);
        cmp.remove(MODULE_LABEL_ID);
        cmp.remove(PAGE_FILTER_ID);
        cmp.remove(PAGE_LABEL_FILTER_ID);
        cmp.doLayout()
    },

    showModuleFilter() {
        this.removePageSubtypeFilters();
        const cmp = Ext.getCmp(FILTER_SIDEBAR_ID)
        cmp.add(this.moduleLabel)
        cmp.add(this.moduleFilterComboConfig);
        cmp.doLayout()
    },

    showPageFilter() {
        this.removePageSubtypeFilters();
        const cmp = Ext.getCmp(FILTER_SIDEBAR_ID)
        cmp.add(this.pageLabel)
        cmp.add(this.pageFilterComboConfig);
        cmp.doLayout()
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

    getPageTypes() {
        // TODO clean this up
        return new Promise((resolve, reject) => {
            fetch("/cms/translation/pages")
                .then((response) => {
                    response.json().then((priorityData) => {
                        resolve(priorityData)
                    })
                })
                .catch((err) => reject(err))
        })
    },
    getModuleTypes() {
        // TODO clean this up
        return new Promise((resolve, reject) => {
            fetch("/cms/translation/modules")
                .then((response) => {
                    response.json().then((priorityData) => {
                        resolve(priorityData)
                    })
                })
                .catch((err) => reject(err))
        })
    },

    renderFlags: function(flags, metaData, record, rowIndex, colIndex, store) {
        // Ensure flags are always shown in the same order, and English comes up first
        const uuid = record.data.handleId;
        flags = flags.sort((a, b) => {
            if (a === "en") return -1;
            if (b === "en") return 1;
            return a.localeCompare(b);
        })

        return flags.map(function(locale) {
            locale = locale === "en" ? "uk" : locale;
            const url = "./wicket/resource/org.hippoecm.frontend.translation.LocaleProviderPlugin/icons/flags/flag-16_" + locale + ".png"
            return '<img src="' + url + '" style="display: inline" />'
        }).join(" ");
    },

    renderDateTime: function(iso) {
        const date = new Date(iso);
        const display = date.dateFormat("d M y")
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
        var self = this;
        this.getModuleTypes()
            .then((types) => self.moduleTypes = types)
            .catch((err) => console.error(err));

        this.getPageTypes()
            .then((types) => self.pageTypes = types)
            .catch((err) => console.error(err));

    },

    destroy: function() {
        Hippo.Reports.TranslationListPanel.superclass.destroy.call(this);
        Hippo.Reports.RefreshObservableInstance.removeListener("refresh", this.loadStore, this);
    }

});

Ext.reg('Hippo.Reports.TranslationListPanel', Hippo.Reports.TranslationListPanel);
