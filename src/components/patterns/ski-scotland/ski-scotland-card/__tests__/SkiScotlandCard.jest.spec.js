import { mount } from '@vue/test-utils';
import moxios from 'moxios';
import VsSkiScotlandCard from '../SkiScotlandCard';

const sampleSkiData = require('./assets/sample-ski-data.json');
const sampleCairngormsData = require('./assets/sample-cairngorms-ski-data.json');

const centreInfoUrl = 'https://testurl';
const centreInfoCairngormsUrl = 'https://testurlcairngorms';

const summaryClosedLabel = 'fermé';
const lastUpdatedLabel = 'dernière mise à jour';
const liftsLabel = 'ascenseurs';
const summaryOpenLabel = 'ouvrir';
const summaryOpeningLabel = 'ouverture';
const summaryOnHoldLabel = 'ouverture';
const summaryLimitedPatrolLabel = 'ouverture';
const runsLabel = 'court';
const imgSrc = 'https://via.placeholder.com/400x200';
const imgAlt = 'Glencoe ski centre';
const moreDetailsLink = '#';
const pisteMapLink = '#';

const factoryMount = (propsData) => mount(VsSkiScotlandCard, {
    propsData: {
        centreInfoUrl,
        summaryClosedLabel,
        lastUpdatedLabel,
        liftsLabel,
        summaryOpenLabel,
        summaryOpeningLabel,
        summaryOnHoldLabel,
        summaryLimitedPatrolLabel,
        runsLabel,
        imgSrc,
        imgAlt,
        moreDetailsLink,
        pisteMapLink,
        ...propsData,
    },
});

describe('VsSkiScotlandCard', () => {
    describe(':props', () => {
        let wrapper;
        beforeEach(() => {
            moxios.install();

            moxios.stubRequest(centreInfoUrl, {
                status: 200,
                response: sampleSkiData,
            });

            wrapper = factoryMount();
        });

        afterEach(() => {
            moxios.uninstall();
        });

        it('should render a component with the data-test attribute `vs-ski-scotland-card`', () => {
            expect(wrapper.find('[data-test="vs-ski-scotland-card"]').exists()).toBe(true);
        });

        it('should not render the js-disabled block when js is present', () => {
            expect(wrapper.find('[data-test="vs-ski-scotland-card__js-disabled"]').exists()).toBe(false);
        });

        it(':summaryClosedLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__closed-label"]');
            expect(container.html()).toContain(summaryClosedLabel);
        });

        it(':summaryOpeningLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__opening-label"]');
            expect(container.html()).toContain(summaryOpeningLabel);
        });

        it(':summaryOpenLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__open-label"]');
            expect(container.html()).toContain(summaryOpenLabel);
        });

        it(':summaryLimitedPatrolLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__limited-patrol-label"]');
            expect(container.html()).toContain(summaryLimitedPatrolLabel);
        });

        it(':summaryOnHoldLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__on-hold-label"]');
            expect(container.html()).toContain(summaryOnHoldLabel);
        });

        it(':lastUpdatedLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__last-updated-label"]');
            expect(container.html()).toContain(lastUpdatedLabel);
        });

        it(':liftsLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__lifts-label"]');
            expect(container.html()).toContain(liftsLabel);
        });

        it(':runsLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__runs-label"]');
            expect(container.html()).toContain(runsLabel);
        });

        it('should correctly display the last updated month in english if the locale is `en-gb` (default)', () => {
            const container = wrapper.find('[data-test="vs-ski__last-updated-label"]');
            expect(container.html()).toContain('January');

            moxios.uninstall();
        });
    });

    describe(':skiScotlandData', () => {
        let wrapper;
        beforeEach(() => {
            moxios.install();

            moxios.stubRequest(centreInfoUrl, {
                status: 200,
                response: sampleSkiData,
            });

            wrapper = factoryMount();
        });

        afterEach(() => {
            moxios.uninstall();
        });

        it('should correctly calculate the number of open, opening and closed runs', () => {
            expect(wrapper.vm.statusSummary.runs.open).toBe(16);
            expect(wrapper.vm.statusSummary.runs.opening).toBe(1);
            expect(wrapper.vm.statusSummary.runs.closed).toBe(2);
            expect(wrapper.vm.statusSummary.runs.limitedPatrol).toBe(2);
            expect(wrapper.vm.statusSummary.runs.onHold).toBe(1);
        });
    });

    describe(':cairngormsData', () => {
        let wrapper;
        beforeEach(() => {
            moxios.install();

            moxios.stubRequest(centreInfoCairngormsUrl, {
                status: 200,
                response: sampleCairngormsData,
            });

            wrapper = factoryMount({
                centreInfoUrl: centreInfoCairngormsUrl,
            });
        });

        afterEach(() => {
            moxios.uninstall();
        });

        it('should correctly calculate the number of open, opening and closed runs', () => {
            expect(wrapper.vm.statusSummary.runs.open).toBe(16);
            expect(wrapper.vm.statusSummary.runs.opening).toBe(0);
            expect(wrapper.vm.statusSummary.runs.closed).toBe(16);
        });
    });

    /* eslint-disable */
    /**
     * This test can't actually be run, toLocaleDateString ignores the provided locale within jest
     * because Node doesn't come with the localisation packages internally. The functionality does
     * appear to work and the second example in the design system renders the date as `Last
     * Updated: 13:18 - 18 janvier 2023` but within a test environment it will provide a false
     * negative.
     *
     * See:
     * https://github.com/facebook/jest/issues/3514
     * https://stackoverflow.com/questions/23199909/using-tolocalestring-in-node-js/23200062#23200062
     */

    // describe(':localisation', () => {
    //  let wrapper;
    //     beforeEach(() => {
    //         moxios.install();

    //         moxios.stubRequest(skiStatusUrl, {
    //             status: 200,
    //             response: sampleSkiData,
    //         });

    //         wrapper = factoryMount({
    //             locale: 'fr-fr',
    //         });
    //     });

    //     afterEach(() => {
    //         moxios.uninstall();
    //     });

    //     it('should correctly display the last updated month in french if the locale is `fr-fr`', () => {
    //         const container = wrapper.find('[data-test="vs-ski__last-updated-label"]');
    //         expect(container.html()).toContain('Janvier');

    //         moxios.uninstall();
    //     });
    // });
    /* eslint-enable */
});
