package br.com.games.gamesranking.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class StringUtil {

	public static boolean isEmptyOrNull(String string) {
		return string == null || string.equals("");
	}
	
	public static boolean isVericaPeloMenosUmaStringNaoNulasOuNaoVazias(String... strings) {

		for (int i = 0; i < strings.length; i++) {
			if (!isEmptyOrNull(strings[i])) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isVerificaTodasStringsNaoNulasOuVazias(Object... strings) {

		int quantidadeStringNaoNulaOuVazias = 0;

		for (int i = 0; i < strings.length; i++) {
			if (!isEmptyOrNull(strings[i].toString())) {
				quantidadeStringNaoNulaOuVazias += 1;
			}
		}

		return quantidadeStringNaoNulaOuVazias == strings.length;
	}
	
	public static String formatarValorEmString(BigDecimal textoValor) {
		return NumberFormat.getCurrencyInstance().format(textoValor);
	}
	
	public static boolean isVerificaStringsIguais(String string1, String string2) {
		return string1.equals(string2);
	}
	
	public static boolean isVerificaStringsDiferentes(String string1, String string2) {
		return !string1.equals(string2);
	}

}
