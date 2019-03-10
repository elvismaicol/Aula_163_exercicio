package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Produto;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Produto> lista = new ArrayList<>();
		
		System.out.println("Enter file path: ");
		String origemArqStr = sc.nextLine();
		
		File origemArq = new File(origemArqStr);
		String origemPastaStr = origemArq.getParent();
		
		boolean sucesso = new File(origemPastaStr + "\\out").mkdir();
		
		String destinoArqStr = origemPastaStr + "\\out\\summary.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader(origemArqStr))) {
			
			String itemCsv = br.readLine();
			while (itemCsv != null) {
				String[] campos = itemCsv.split(",");
				String nome = campos[0];
				double preco = Double.parseDouble(campos[1]);
				int quantidade = Integer.parseInt(campos[2]);
				
				lista.add(new Produto(nome, preco, quantidade));
				
				itemCsv = br.readLine();
			}
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(destinoArqStr))) {
				
				for (Produto item : lista) {
					bw.write(item.getNome() + "," + String.format("%.2f", item.Total()));
					bw.newLine();
				}
				
				System.out.println(destinoArqStr + "    Criado!!!");
				
			} catch (IOException e) {
				System.out.println("Error write file: " + e.getMessage());
			}
			
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());					
		}
		
		sc.close();			
	}
}
