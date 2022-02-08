import { shallowMount } from '@vue/test-utils';
import VsForm from '../Form';

jest.mock('axios', () => ({
    get: () => Promise.resolve(
        {
            data: {
                fields: [
                    {
                        name: 'firstName',
                        element: 'input',
                        type: 'text',
                        label: 'First name',
                        validation: {
                            required: true,
                        },
                    },
                    {
                        name: 'lastName',
                        element: 'input',
                        type: 'text',
                        label: 'Last name',
                    },
                    {
                        name: 'email',
                        element: 'input',
                        type: 'email',
                        label: 'Email address',
                    },
                ],
            },
        },
    ),
}));

const factoryShallowMount = (propsData) => shallowMount(VsForm, {
    slots: {
        submitError: 'error text',
        invalid: 'invalid text',
        submitting: 'submitting text',
        submitted: 'submitted text',
    },
    props: {
        dataUrl: 'testUrl',
        ...propsData,
    },
});

beforeEach(() => {
    window.MktoForms2 = {
        loadForm: jest.fn(),
    };
});

describe('VsForm', () => {
    it('should render a component with the data-test attribute `vs-form`', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.find('[data-test="vs-form"]').exists()).toBe(true);
    });

    it('should render three input fields', async() => {
        const wrapper = factoryShallowMount();
        await wrapper.vm.$nextTick();
        const allInputs = wrapper.findAll('label');

        expect(allInputs.length).toBe(3);
    });

    it('should render `required` on required inputs', async() => {
        const wrapper = factoryShallowMount();
        await wrapper.vm.$nextTick();
        const allInputs = wrapper.findAll('label');

        expect(allInputs.at(0).text()).toContain('required');
    });

    describe(':slots', () => {
        it('should render the `submitting` slot', async() => {
            const wrapper = factoryShallowMount();
            wrapper.vm.submitting = true;

            await wrapper.vm.$nextTick();

            expect(wrapper.html()).toContain('submitting text');
        });

        it('should render the `submitted` slot', async() => {
            const wrapper = factoryShallowMount();
            wrapper.vm.submitted = true;

            await wrapper.vm.$nextTick();

            expect(wrapper.html()).toContain('submitted text');
        });

        it('should render the `submitError` slot', async() => {
            const wrapper = factoryShallowMount();
            wrapper.vm.submitError = true;

            await wrapper.vm.$nextTick();

            expect(wrapper.html()).toContain('error text');
        });

        it('should render the `invalid` slot', async() => {
            const wrapper = factoryShallowMount();
            wrapper.vm.showErrorMessage = true;
            wrapper.vm.errorFields = ['firstname'];

            await wrapper.vm.$nextTick();

            expect(wrapper.html()).toContain('invalid text');
        });
    });
});
