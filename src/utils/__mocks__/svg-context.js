const mockSvg = require("./svg-mock.js")

const mockFunc = jest.fn()

mockFunc.mockReturnValue(mockSvg)

module.exports = mockFunc
