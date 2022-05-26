import noJsClass from '@/utils/no-js-class';

const jsIsDisabled = () => {
    const elements = document.getElementsByClassName(noJsClass);

    return elements;
};

export default jsIsDisabled;
