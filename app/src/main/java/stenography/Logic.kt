package stenography

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import kotlin.math.ceil

const val END_MARKER = "\u0000\u0000\u0003"

open class SteganographyCryptography {

    /**
     * When the hide command is given and the secret message is input, the user should be prompted for a password with the message Password:.
     *
     * The program reads the password string and converts it to a Bytes Array. The first message byte will be XOR encrypted using the first password byte, the second message byte will be XOR encrypted with the second password byte, and so on. If the password is shorter than the message, then after the last byte of the password, the first byte of the password should be used again.
     *
     * Three Bytes with values 0, 0, 3 should be added to the encrypted Bytes Array.
     *
     * If the image size is adequate for holding the Bytes array, the result will be hidden in the image.
     */
    protected fun hideCommand(inputFilename: String, outputFilename: String, message: String, providedPassword: String): Array<String> {
        var password = providedPassword

        if (password.length < message.length) {
            password = extendPassword(message, password)
        }

        if (password.length > message.length) {
            password = password.substring(0, message.length)
        }

        val encryptedBytes = processMessageByPassword(message, password)

        try {
            val image = ImageIO.read(File(inputFilename))

            binaryStringOf(encryptedBytes).forEachIndexed { index, c ->
                val x = index % image.width
                val y = index / image.width
                val bit = c.digitToInt()

                val color = Color(image.getRGB(x, y))
                val newColor = Color(color.red, color.green, getNewBlueValue(color.blue, bit))
                image.setRGB(x, y, newColor.rgb)
            }

            ImageIO.write(image, "png", File(outputFilename))
            return arrayOf("INFORMATION", "Success!", "Message saved in $outputFilename image.")

        } catch (ex: IndexOutOfBoundsException) {
            return arrayOf("ERROR", "Error!", "The input image is not large enough to hold this message.")
        } catch (ex: IOException) {
            return arrayOf("ERROR", "Error!", "Something went wrong!")
        }
    }

    /**
     * Additional function for hideCommand
     * @param message with a bigger size
     * @param password to extend
     * @return extended password
     */
    private fun extendPassword(message: String, password: String): String {
        val diffRounded = ceil((message.length.toDouble() / password.length)).toInt()
        return password.repeat(diffRounded).substring(0, message.length)
    }

    /**
     * Additional function for hideCommand
     * @param message to encodeToByteArray
     * @return byteArray as a string
     */
    private fun binaryStringOf(message: String): String {
        return (message + END_MARKER)
            .encodeToByteArray()
            .joinToString("") { it.toString(2).padStart(8, '0') }
    }

    /**
     * Additional function for hideCommand
     * @param blueByte the blue component in the range 0-255 in the default sRGB space.
     * @param newBit 1 or 0 from binary string
     * @return new blue component in the range 0-255 in the default sRGB space
     */
    private fun getNewBlueValue(blueByte: Int, newBit: Int): Int {
        val isLastBitOfBlueByteOne = blueByte % 2 == 1
        val isNewBitEqualsOne = newBit == 1

        return if (isLastBitOfBlueByteOne xor isNewBitEqualsOne) { // true / false OR false / true
            blueByte xor 1 // alter last bit
        } else {
            blueByte // without changing
        }
    }


    protected fun showCommand(inputFilename: String, providedPassword: String): Array<String> {

        return try {
            val image = ImageIO.read(File(inputFilename))
            val extractedBinaryString = extractBinaryString(image)
                .chunked(8)
                .map { byte -> byte.toInt(2).toChar() }
                .joinToString("")
                .split(END_MARKER)
                .first()

            val decryptedMessage = processMessageByPassword(extractedBinaryString, providedPassword)
            arrayOf("INFORMATION", "Success", "Decrypted message is: $decryptedMessage")

        } catch (ex: IOException) {
            arrayOf("ERROR", "Error!", "Something went wrong!")
        }
    }

    private fun extractBinaryString(image: BufferedImage): String {
        val binaryString = StringBuilder()
        for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                if (binaryString.contains("000000000000000000000011".toRegex())) {
                    break
                }
                val color = Color(image.getRGB(x, y))
                val lastBit = color.blue.toString(2).last()
                binaryString.append(lastBit)
            }
        }
        return binaryString.toString()
    }

    /**
     * @param message encrypted or decrypted
     * @param oldPassword for encryption or decryption
     * @return encrypted or decrypted message
     */
    private fun processMessageByPassword(message: String, oldPassword: String): String {
        var updatedPassword = oldPassword

        if (oldPassword.length < message.length) {
            updatedPassword = extendPassword(message, oldPassword)
        }

        if (oldPassword.length > message.length) {
            updatedPassword = oldPassword.substring(0, message.length)
        }

        val size = updatedPassword.length

        val encodedMessage = message.toByteArray()
        val encodedPassword = updatedPassword.toByteArray()

        val mergedList = mutableListOf<Byte>()

        for (i in 0 until size) {
            val operatedBit = encodedMessage[i].toInt() xor encodedPassword[i].toInt()
            mergedList.add(operatedBit.toByte())
        }

        return mergedList.map { it.toInt().toChar() }.joinToString("")
    }
}