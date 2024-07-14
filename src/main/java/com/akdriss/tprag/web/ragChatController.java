package com.akdriss.tprag.web;

import com.akdriss.tprag.service.ChatAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("chat")
@RestController
@RequiredArgsConstructor
public class ragChatController {

    private final ChatAiService chatAiService;
    private final VectorStore vectorStore;
    @Value("classpath:/prompts/prompts.st")
    private  Resource promptTemplate;

    @GetMapping("/ask")
    public String ragAsk(String question){
        PromptTemplate pt = new PromptTemplate(promptTemplate);
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(question).withTopK(4));
        //context
        List<String> context = documents.stream().map(Document::getContent).toList();
        //prompt template you can use default values on the constuctor
        Prompt prompt = pt.create(Map.of("context", context, "question", question));
        return chatAiService.ragChat(prompt);
    }

}
