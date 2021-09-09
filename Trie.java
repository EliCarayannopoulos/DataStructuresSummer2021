package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/
		                      
	    TrieNode root = new TrieNode(null, null, null);
        TrieNode nextChild = root;
        TrieNode nextSibling = root;
        
        if (allWords.length == 0) {
        	
        	return null;
        	
        }
        
        for (int i = 0; i < allWords.length; i++) {
            
            // This occurs only once. Adds in the first string of the array, so that the trie is
            // not empty.
            
            
            if (i == 0) {
                short beginning = 0;
                short ending = 0;
                for (short j = 0; j < allWords[i].length(); j++) {
                    if (j + 1 == allWords[i].length()) {
                        ending = j;
                    }
                }
                Indexes index = new Indexes(0, beginning, ending);
                TrieNode wordNode = new TrieNode(index, null, null);
                root.firstChild = wordNode;
                continue;
            }
            
            // Every single string following the first one will always go into this loop. 
            // Checks 
            
            TrieNode ptr = root.firstChild;
            TrieNode prev = root;
            String currentWord = allWords[i];
            short startingPoint = 0;
            
            while (ptr != null) {
            	
            	
            	// This condition checks for words that would share the same parent
                // Rearranges the current node
                // Creates the parents and siblings nodes that fall within the parent
        
            	
                if (currentWord.charAt(startingPoint) == allWords[ptr.substr.wordIndex].charAt(startingPoint)) {
                    
                    short ending = 0;
                    for (short j = startingPoint; j < currentWord.length(); j++) {
                    	if (currentWord.charAt(j) != allWords[ptr.substr.wordIndex].charAt(j)) {
                    		break;
                    	}
                    	if (j > ptr.substr.endIndex) {
                    		break;
                    	}
                    	ending = j;
                    }
                    
                    if (ending == ptr.substr.endIndex) {
                    	
                    	startingPoint = (short) ((short) ending + 1);
                    	
                    	prev = ptr;
                    	ptr = ptr.firstChild;
                    	
                    	continue;
                    	
                    	
                    } else {
                    	
                 
                    	Indexes index = new Indexes(ptr.substr.wordIndex, startingPoint, ending);
                    	TrieNode node = new TrieNode(index, null, null);
                    	
                    	Indexes childIndex = new Indexes(ptr.substr.wordIndex, (short) ((short) ending + 1), ptr.substr.endIndex);
                    	TrieNode childNode = new TrieNode(childIndex, null, null);
                    	
                    	Indexes siblingIndex = new Indexes(i, (short) ((short) ending + 1), (short) ((short)currentWord.length() - 1));
                    	TrieNode siblingNode = new TrieNode(siblingIndex, null, null);
                    	
                    	if (prev == root || prev.firstChild == ptr) {
                    		prev.firstChild = node;
                    	} else {
                    		prev.sibling = node;
                    	}
                    	
                    	childNode.sibling = siblingNode;
                    	node.firstChild = childNode;
                    	node.sibling = ptr.sibling;
                    	
                    	if (ptr.firstChild != null) {
                    		childNode.firstChild = ptr.firstChild;
                    	}
                    	
                    	break;
                    }
            
                
                } else if (ptr.sibling == null && currentWord.charAt(startingPoint) != allWords[ptr.substr.wordIndex].charAt(startingPoint)){
                	
                	short ending = 0;
                	
                	for (short j = startingPoint; j < currentWord.length(); j++) {
                		ending = j;
                	}
                	
                	Indexes index = new Indexes(i, startingPoint, ending);
                	TrieNode node = new TrieNode(index, null, null);
                
                    ptr.sibling = node;
                    
                    break;
               
                } else {
                    
                	prev = ptr;
                    ptr = ptr.sibling;
                
                }
            }
        }
			
		return root;
	}
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/
		
		ArrayList<TrieNode> prefixList = new ArrayList<TrieNode>();
		TrieNode ptr = root.firstChild;
		String prefixBuilder = "" ;
		
		if (ptr == null || allWords.length == 0 || prefix.length() == 0) {
			
			return null;
			
		}
		
		while (ptr != null) {
			
			if (prefix.charAt(ptr.substr.startIndex) == allWords[ptr.substr.wordIndex].charAt(ptr.substr.startIndex)) {
				//you want to check if ALL the letters are equal, not just the first character
				
				
				for (int i = ptr.substr.startIndex; i <= ptr.substr.endIndex; i++) {
					
					if (i == prefix.length()) {
						break;
					}
					
					if (prefix.charAt(i) != allWords[ptr.substr.wordIndex].charAt(i)) {
						
						return null;
						
					}
					
					prefixBuilder += prefix.charAt(i);
					
					if (prefixBuilder.equals(prefix)) {
						break;
					}
				
				}
				
				if (prefixBuilder.equals(prefix)) {
					
					if (ptr.firstChild == null) {
						
						prefixList.add(ptr);
						break;
						
					}
					
					TrieNode runner = ptr.firstChild;
					
							prefixList = recurseMethod(runner, prefixList);
					
					break;
					
				} else {
					
					ptr = ptr.firstChild;
					
					continue;
				
				}
				
			} else {
				
				ptr = ptr.sibling;
			
			}
			
		}
		
		if (prefixBuilder == "" || prefixList == null || !(prefixBuilder.equals(prefix))) {
			
			return null;
			
		}
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		return prefixList;
	}
	
	private static ArrayList<TrieNode> recurseMethod(TrieNode runner, ArrayList<TrieNode> prefixList) {
		
		while (runner != null) {
			if (runner.firstChild == null) {
				System.out.println("How many times do you loop the recursion man?");
				prefixList.add(runner);
			} else {
				prefixList = recurseMethod(runner.firstChild, prefixList);
			}
			
			runner = runner.sibling;
			
		}
		
		return prefixList;
		
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }