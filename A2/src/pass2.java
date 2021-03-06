import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;


public class pass2 {
	ArrayList registers;
	HashMap symtab, littab;
	int lc;
	
	public pass2(){
		registers = new ArrayList();
		symtab = new HashMap();
		littab = new HashMap();
		registers.add("AREG,");
		registers.add("BREG,");
		lc = 0;
	}
	
	public void print(Object o){ System.out.println(o); }
	
	public void read_tables() throws IOException{
		//Add symbols
		BufferedReader br = new BufferedReader(new FileReader("../A1/symbol"));
		String line;
		while((line = br.readLine()) != null){
			String [] words = line.split("\t");
			symtab.put(words[0], words[1]);
		}
		br.close();
		
		//Add literals
		br = new BufferedReader(new FileReader("../A1/literal"));
		
		while((line = br.readLine()) != null){
			String [] words = line.split("\t");
			littab.put(words[0], words[1]);
		}
	}
	
	public void parse_intermediate_code() throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader("../A1/intermediate_code"));
		PrintWriter pr = new PrintWriter("target", "utf-8");
		
		String line;
		
		//Get line counter
		if((line = br.readLine()) != null){
			if(line.split("\t")[0].equalsIgnoreCase("(AD,01)")){
				lc = Integer.parseInt(line.split("\t")[1]);
			}
			else{
				print("Error");
			}
		}
		
		
		while((line = br.readLine()) != null){
			
			String [] words = line.split("\t");
			String sign = "+";
			String opcode;
			String reg;
			int mem_add, lit_addr;
			
			// Get opcode
			if(words.length >= 2){
				// If it is IS 
				if(words[0].split(",")[0].split("\\(")[1].equalsIgnoreCase("IS")){
					
					opcode = words[0].split(",")[1].split("\\)")[0];
					if(opcode.equalsIgnoreCase("09") || opcode.equalsIgnoreCase("10")){
						reg = "0";
						mem_add = Integer.parseInt((String) symtab.get(words[1]));
						pr.write(lc + "\t" + sign + "\t" + opcode + "\t" + reg + "\t" + mem_add);
						lc += 1;
					}
					else if(words[2].matches("='[0-9]*'")){
						reg = Integer.toString(registers.indexOf(words[1])+1);
						lit_addr = Integer.parseInt((String) littab.get(words[2].split("=")[1]));
						pr.write(lc + "\t" + sign + "\t" + opcode + "\t" + reg + "\t" + lit_addr);
						lc += 1;
					}
					else{
						reg = Integer.toString(registers.indexOf(words[1])+1);
						if(symtab.containsKey(words[2]))
							mem_add = Integer.parseInt((String) symtab.get(words[2]));
						else if(littab.containsKey(words[2]))
							mem_add = Integer.parseInt((String) littab.get(words[2]));
						else
							mem_add = 0;
						pr.write(lc + "\t" + sign + "\t" + opcode + "\t" + reg + "\t" + mem_add);
						lc += 1;
					}
					pr.write("\n");
				}
				//If it is DS
				else if(words[0].split(",")[0].split("\\(")[1].equalsIgnoreCase("DS")){
					opcode = words[0].split(",")[1].split("\\)")[0];
					if(opcode.equalsIgnoreCase("01")){
						pr.write(lc + "\t" + sign + "\t" + "00\t0\t" + words[1].split("'")[1]);
						lc += 1;
						pr.write("\n");
					}
					else{
						int temp;
						for(int i = 0;i < Integer.parseInt(words[1]);i ++){
							temp = lc + i;
							//pr.write(temp);
							//pr.write("\n");
						}
						lc += Integer.parseInt(words[1]);
						
					}
				}
				//If it is AD deal only with origin
				else if(words[0].equalsIgnoreCase("(AD,03)")){
					String label[] = words[1].split("\\+");
					if(label.length == 1){
						lc = Integer.parseInt((String) symtab.get(label[0]));
					}
					else if(label.length == 2){
						lc = Integer.parseInt((String) symtab.get(label[0])) + Integer.parseInt(label[1]);
					}
				}
			}
			
		}
		pr.close();
		br.close();
	}
	
	public static void main(String args[]) throws IOException{
		pass2 p2 = new pass2();
		p2.read_tables();
		p2.parse_intermediate_code();
	}
}
